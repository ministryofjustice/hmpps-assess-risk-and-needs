package uk.gov.justice.digital.hmpps.assessrisksandneeds.api.model

import com.fasterxml.jackson.annotation.JsonView
import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.assessrisksandneeds.api.model.RiskLevel.HIGH
import uk.gov.justice.digital.hmpps.assessrisksandneeds.api.model.RiskLevel.LOW
import uk.gov.justice.digital.hmpps.assessrisksandneeds.api.model.RiskLevel.MEDIUM
import uk.gov.justice.digital.hmpps.assessrisksandneeds.api.model.RiskLevel.VERY_HIGH
import java.time.LocalDateTime

data class RoshRiskWidgetDto(
  @Schema(description = "Has a RoSH risk assessment been complete?", example = "true")
  @JsonView(View.CrsProvider::class, View.RiskView::class)
  val hasBeenCompleted: Boolean? = null,

  @Schema(description = "Overall ROSH risk score", example = "VERY_HIGH")
  @JsonView(View.CrsProvider::class, View.RiskView::class)
  val overallRisk: String? = null,

  @Schema(description = "Assessed on", example = "2021-10-10")
  @JsonView(View.Probation::class, View.SingleRisksView::class)
  val lastUpdated: LocalDateTime? = null,

  @Schema(
    description = "Risk in the community",
    example = "{" +
      "  \"Public\": \"HIGH\"" +
      "  \"Children\": \"LOW\"" +
      "  \"Known Adult\": \"MEDIUM\"" +
      "  \"Staff\": \"VERY_HIGH\"" +
      "}"
  )
  @JsonView(View.CrsProvider::class, View.RiskView::class)
  val riskInCommunity: Map<String, String?> = hashMapOf(),

  @Schema(
    description = "Risk in custody",
    example = "{" +
      "  \"Public\": \"HIGH\"" +
      "  \"Children\": \"LOW\"" +
      "  \"Known Adult\": \"MEDIUM\"" +
      "  \"Staff\": \"VERY_HIGH\"" +
      "  \"Prisoners\": \"MEDIUM\"" +
      "}"
  )
  @JsonView(View.Probation::class, View.RiskView::class)
  val riskInCustody: Map<String, String?> = hashMapOf(),
) {
  companion object {
    fun riskLevelToString(riskLevel: RiskLevel?): String? {
      return when (riskLevel) {
        VERY_HIGH -> "VERY_HIGH"
        HIGH -> "HIGH"
        MEDIUM -> "MEDIUM"
        LOW -> "LOW"
        null -> null
      }
    }

    fun mapRiskLevelsToStrings(risks: Map<String, RiskLevel?>): Map<String, String?> {
      return risks.map {
        it.key to riskLevelToString(it.value)
      }.toMap()
    }
  }
}
