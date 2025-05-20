package com.example.leapit.jobposting;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JobPostingRepository {
    private final EntityManager em;

    // 채용공고 저장
    public JobPosting save(JobPosting jobPosting) {
        em.persist(jobPosting);
        return jobPosting;
    }

    // 아이디로 채용공고 찾기
    public Optional<JobPosting> findById(Integer id) {
        return Optional.ofNullable(em.find(JobPosting.class, id));
    }


    // COMPANY의 채용공고 & 해당 채용공고의 기술스택 조회
    public List<Object[]> findByUserIdJoinJobPostingTechStacks(Integer userId) {
        Query query = em.createQuery(
                "SELECT j, t FROM JobPosting j " +
                        "LEFT JOIN JobPostingTechStack t ON t.jobPosting.id = j.id " +
                        "WHERE j.user.id = :userId" + " ORDER BY j.deadline DESC "
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // COMPANY가 채용중인 포지션 카운트 조회 (마감일이 오늘 이후인)
    public Long countByUserIdAndDeadlineAfter(Integer userId) {
        Query query = em.createQuery("SELECT COUNT(j) FROM JobPosting j WHERE j.user.id = :userId AND j.deadline >= CURRENT_DATE");
        query.setParameter("userId", userId);
        return (Long) query.getSingleResult();
    }

    // 아이디로 채용공고 삭제
    public void deleteById(Integer id) {
        em.remove(em.find(JobPosting.class, id));
    }

    // 전체 공고 조회
    public List<JobPostingResponse.ListDTO> findAllByCompanyUserId(Integer companyUserId) {
        String jpql = """
                    SELECT new com.example.leapit.jobposting.JobPostingResponse$ListDTO(
                        jpt.id,
                        jpt.title
                    )
                    FROM JobPosting jpt
                    JOIN jpt.user u
                    WHERE u.id = :companyUserId
                    ORDER BY jpt.createdAt DESC
                """;

        return em.createQuery(jpql, JobPostingResponse.ListDTO.class)
                .setParameter("companyUserId", companyUserId)
                .getResultList();
    }

    // 진행중인 공고 조회
    public List<JobPostingResponse.ListDTO> findOpenByCompanyUserId(Integer companyUserId) {
        String jpql = """
                    SELECT new com.example.leapit.jobposting.JobPostingResponse$ListDTO(
                        jpt.id,
                        jpt.title
                    )
                    FROM JobPosting jpt
                    JOIN jpt.user u
                    WHERE u.id = :companyUserId
                      AND jpt.deadline >= CURRENT_DATE
                    ORDER BY jpt.createdAt DESC
                """;

        return em.createQuery(jpql, JobPostingResponse.ListDTO.class)
                .setParameter("companyUserId", companyUserId)
                .getResultList();
    }

    // 마감된 공고 조회
    public List<JobPostingResponse.ListDTO> findClosedByCompanyUserId(Integer companyUserId) {
        String jpql = """
                    SELECT new com.example.leapit.jobposting.JobPostingResponse$ListDTO(
                        jpt.id,
                        jpt.title
                    )
                    FROM JobPosting jpt
                    JOIN jpt.user u
                    WHERE u.id = :companyUserId
                      AND jpt.deadline < CURRENT_DATE
                    ORDER BY jpt.createdAt DESC
                """;

        return em.createQuery(jpql, JobPostingResponse.ListDTO.class)
                .setParameter("companyUserId", companyUserId)
                .getResultList();

    }


    // 구직자 메인페이지 - 인기 공고 8개 + 기술스택까지 조인해서 조회
    public List<Object[]> findTop8PopularJobPostingsAndTechStacks() {
        LocalDate today = LocalDate.now();

        // 1단계: 마감일이 지나지 않은 공고 중에서 viewCount 기준으로 상위 8개의 공고 ID만 추출
        List<Integer> top8Ids = em.createQuery(
                        "SELECT jp.id FROM JobPosting jp " +
                                "WHERE jp.deadline >= :today " +
                                "ORDER BY jp.viewCount DESC, jp.createdAt DESC", Integer.class)
                .setParameter("today", LocalDate.now())
                .setMaxResults(8)
                .getResultList();

        // 아무 공고도 없으면 빈 리스트 반환
        if (top8Ids.isEmpty()) return new ArrayList<>();

        // 2단계: 위에서 뽑은 8개 ID에 대해, 공고와 기술스택을 LEFT JOIN으로 함께 조회
        // 결과는 Object[] 형태 (Object[0] = JobPosting, Object[1] = JobPostingTechStack)
        return em.createQuery(
                        "SELECT jp, jpts FROM JobPosting jp " +
                                "LEFT JOIN JobPostingTechStack jpts ON jp.id = jpts.jobPosting.id " +
                                "WHERE jp.id IN :ids " +
                                "ORDER BY jp.viewCount DESC", Object[].class)
                .setParameter("ids", top8Ids)
                .getResultList();
    }


    // 구직자 메인페이지 - 인기 공고 8개의 ID만 가져오기 (순서 보장용)
    public List<Integer> findTop8PopularJobPostings() {
        return em.createQuery(
                        "SELECT jp.id FROM JobPosting jp " +
                                "WHERE jp.deadline >= :today " +
                                "ORDER BY jp.viewCount DESC, jp.createdAt DESC", Integer.class)
                .setParameter("today", LocalDate.now())
                .setMaxResults(8)
                .getResultList();
    }

    // 주소 - 시 조회
    public String findByRegion(Integer id) {
        Query query = em.createNativeQuery("select R.name from job_posting_tb J inner join  region_tb R  on R.id = J.address_region_id where J.id = ?");
        query.setParameter(1, id);

        return (String) query.getSingleResult();
    }

    // 주소 - 구 조회
    public String findBySubRegion(Integer id) {
        Query query = em.createNativeQuery("select S.name from job_posting_tb J inner join  sub_region_tb S  on S.id = J.address_sub_region_id where J.id = ?");
        query.setParameter(1, id);
        return (String) query.getSingleResult();

    }

    // 구직자 메인페이지 - 최신공고 3개
    public List<JobPosting> findTop3RecentJobPostings() {
        LocalDate today = LocalDate.now();

        Query query = em.createQuery(
                "SELECT jp FROM JobPosting jp " +
                        "WHERE jp.deadline >= :today " +
                        "ORDER BY jp.createdAt DESC"
        );
        query.setParameter("today", today);
        query.setMaxResults(3);

        return query.getResultList();

    }
}

