package com.example.leapit.companyinfo;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@Import(CompanyInfoRepository.class)
@DataJpaTest
public class companyInfoRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private CompanyInfoRepository CIR;
    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Test
    public void findByUsername_test() {
        // given
        Integer userId = 7;

        // when
        Optional<CompanyInfo> companyInfoOP = companyInfoRepository.findByUserId(userId);

        // eye
        System.out.println("===========유저네임중복체크============");
        System.out.println(companyInfoOP.get().getId());
        System.out.println(companyInfoOP.get().getCompanyName());
        System.out.println(companyInfoOP.get().getAddress());
        System.out.println("===========유저네임중복체크============");
    }

    @Test
    public void findById_test() {
        Integer userId = 7;
        Optional<CompanyInfo> companyInfoOP = companyInfoRepository.findById(userId);
        System.out.println("===========유저네임중복체크============");
        System.out.println(companyInfoOP.get().getId());
        System.out.println(companyInfoOP.get().getCompanyName());
        System.out.println(companyInfoOP.get().getAddress());
        System.out.println("===========유저네임중복체크============");
    }
}
