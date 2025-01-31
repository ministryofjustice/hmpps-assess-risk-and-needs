package uk.gov.justice.digital.hmpps.assessrisksandneeds.api.model

import com.fasterxml.jackson.annotation.JsonView
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class RiskRoshSummaryDto(

  @Schema(description = "Who is at risk?", example = "X, Y and Z are at risk")
  @JsonView(View.Hmpps::class, View.RiskView::class)
  val whoIsAtRisk: String? = null,

  @Schema(description = "What is the nature of the risk?", example = "The nature of the risk is X")
  @JsonView(View.Hmpps::class, View.RiskView::class)
  val natureOfRisk: String? = null,

  @Schema(
    description = "When is the risk likely to be greatest. Consider the timescale and indicate whether risk is immediate or not. " +
      "Consider the risks in custody as well as on release.",
    example = "the risk is imminent and more probably in X situation",
  )
  @JsonView(View.Hmpps::class, View.RiskView::class)
  val riskImminence: String? = null,

  @Schema(
    description = "What circumstances are likely to increase risk." +
      " Describe factors, actions, events which might increase level of risk, now and in the future.",
    example = "If offender in situation X the risk can be higher",
  )
  @JsonView(View.Hmpps::class, View.RiskView::class)
  val riskIncreaseFactors: String? = null,

  @Schema(
    description = "What factors are likely to reduce the risk. Describe factors, actions, and events which may reduce " +
      "or contain the level of risk. What has previously stopped him / her?",
    example = "Giving offender therapy in X will reduce the risk",
  )
  @JsonView(View.Hmpps::class, View.RiskView::class)
  val riskMitigationFactors: String? = null,

  @Schema(
    description = "Assess the risk of serious harm the offender poses in the community",
    example = " " +
      "{" +
      "    \"HIGH \": [\"Children\",\"Public\",\"Know adult\"]," +
      "    \"MEDIUM\": [ \"Staff\"]," +
      "    \"LOW\": [\"Prisoners\"]" +
      "}",
  )
  @JsonView(View.CrsProvider::class, View.RiskView::class)
  val riskInCommunity: Map<RiskLevel?, List<String>> = mapOf(),

  @Schema(
    description = "Assess the risk of serious harm the offender poses on the basis that they could be released imminently back into the community." +
      "Assess both the risk of serious harm the offender presents now, in custody, and the risk they could present to others whilst in a custodial setting.",
    example = " " +
      "{" +
      "    \"HIGH \": [\"Know adult\"]," +
      "    \"VERY_HIGH\": [ \"Staff\", \"Prisoners\"]," +
      "    \"LOW\": [\"Children\",\"Public\"]" +
      "}",
  )
  @JsonView(View.Hmpps::class, View.RiskView::class)
  val riskInCustody: Map<RiskLevel?, List<String>> = mapOf(),

  @Schema(description = "The date and time that the assessment was completed")
  @JsonView(View.Hmpps::class, View.SingleRisksView::class)
  val assessedOn: LocalDateTime? = null,
) {
  @Schema(
    description = "Overall Risk Level",
    example = "HIGH",
  )
  @JsonView(View.CrsProvider::class, View.RiskView::class)
  val overallRiskLevel: RiskLevel? = (riskInCommunity.keys + riskInCustody.keys)
    .sortedBy { it?.ordinal }
    .firstOrNull()
}

enum class RiskLevel(

  val value: String,
) {
  VERY_HIGH("Very High"),
  HIGH("High"),
  MEDIUM("Medium"),
  LOW("Low"),
  ;

  companion object {
    fun fromString(enumValue: String?): RiskLevel? = if (enumValue == null) {
      null
    } else {
      entries.firstOrNull { it.value == enumValue }
        ?: throw IllegalArgumentException("Unknown Risk Level $enumValue")
    }
  }
}
