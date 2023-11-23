package me.dio.credit.application.system.service

import me.dio.credit.application.system.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

class CreditServiceTest {
    @Autowired lateinit var customerRepository: CustomerRepository
    @Autowired lateinit var testEntityManager: TestEntityManager


}