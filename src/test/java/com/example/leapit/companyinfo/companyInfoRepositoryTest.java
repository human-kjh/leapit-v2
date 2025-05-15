package com.example.leapit.companyinfo;

import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoRepository;
import com.example.leapit.user.User;
import com.example.leapit.user.UserRepository;
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
    private CompanyInfoRepository companyInfoRepository;

    @Test
    public void findByUsername_test() {
        Integer userId = 7;
        Optional<CompanyInfo> companyInfoOP = companyInfoRepository.findByUserId(userId);
        System.out.println("===========유저네임중복체크============");
        System.out.println(companyInfoOP.get().getId());
        System.out.println(companyInfoOP.get().getCompanyName());
        System.out.println(companyInfoOP.get().getAddress());
        System.out.println("===========유저네임중복체크============");
    }
}
