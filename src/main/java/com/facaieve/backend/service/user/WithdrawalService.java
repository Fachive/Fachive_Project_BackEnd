package com.facaieve.backend.service.user;

import com.facaieve.backend.entity.user.WithdrawalEntity;
import com.facaieve.backend.repository.user.WithdrawalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service

public class WithdrawalService {
    private WithdrawalRepository withdrawalRepository;

    public WithdrawalEntity createWithdrawal(WithdrawalEntity withdrawalEntity){
        return null;
    }
}
