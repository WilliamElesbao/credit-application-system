package me.dio.credit.application.system.dto


import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import java.time.LocalDate
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [FutureDateValidator::class])
annotation class MaxThreeMonthsAfterCurrentDate(
    val message: String = "A data da primeira parcela deve ser no máximo 3 mêses após o dia atual",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class FutureDateValidator:ConstraintValidator<MaxThreeMonthsAfterCurrentDate, LocalDate> {
      override fun isValid(value: LocalDate?, context: ConstraintValidatorContext?):Boolean{
          if (value == null){
              return true // null values should be handled by @NotNull or @Future
          }
          val currentDate = LocalDate.now()
          val threeMonthsAfter = currentDate.plusMonths(3)
          return value.isBefore(threeMonthsAfter) || value.isEqual(threeMonthsAfter)
      }
}