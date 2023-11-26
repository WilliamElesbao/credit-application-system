package me.dio.credit.application.system.service.impl

import MaxThreeMonthsAfterCurrentDateException
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.service.ICreditService
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.time.LocalDate
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
): ICreditService {
    private fun isInvalidFirstInstallmentDate(date: LocalDate?): Boolean {
        if (date == null) {
            return false // Ou você pode considerar isso inválido e lançar outra exceção
        }
        val currentDate = LocalDate.now()
        val threeMonthsAfter = currentDate.plusMonths(3)
        return date.isAfter(threeMonthsAfter)
    }
    override fun save(credit: Credit): Credit {
        if (isInvalidFirstInstallmentDate(credit.dayFirstInstallment)) {
            throw MaxThreeMonthsAfterCurrentDateException("A data da primeira parcela deve ser no máximo 3 meses após o dia atual")
        }

        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomer(custumerId: Long): List<Credit> =
        this.creditRepository.findAllByCustomerId(custumerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit = (this.creditRepository.findByCreditCode(creditCode)
            ?: throw BusinessException("CreditCode $creditCode not found"))
        return if(credit.customer?.id == customerId) credit else throw IllegalArgumentException("Contact admin")
        /*
        if (credit.customer?.id == customerId) {
            return credit
        } else {
            throw RuntimeException("Contact admin")
        }
        */
    }
}