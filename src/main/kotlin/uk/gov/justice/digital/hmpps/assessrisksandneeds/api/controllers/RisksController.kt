package uk.gov.justice.digital.hmpps.assessrisksandneeds.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.assessrisksandneeds.api.model.AllRoshRiskDto
import uk.gov.justice.digital.hmpps.assessrisksandneeds.api.model.OtherRoshRisksDto
import uk.gov.justice.digital.hmpps.assessrisksandneeds.api.model.RiskRoshSummaryDto
import uk.gov.justice.digital.hmpps.assessrisksandneeds.api.model.RoshRiskToSelfDto
import uk.gov.justice.digital.hmpps.assessrisksandneeds.services.RiskService

@RestController
class RisksController(private val riskService: RiskService) {

  @RequestMapping(path = ["/risks/crn/{crn}/summary"], method = [RequestMethod.GET])
  @Operation(description = "Gets rosh summary for crn")
  @ApiResponses(
    value = [
      ApiResponse(responseCode = "403", description = "Unauthorized"),
      ApiResponse(responseCode = "404", description = "CRN Not Found"),
      ApiResponse(responseCode = "200", description = "OK")
    ]
  )
  @PreAuthorize("hasAnyRole('ROLE_PROBATION', 'ROLE_CRS_PROVIDER') and hasAuthority('SCOPE_read')")
  fun getRiskSummaryByCrn(
    @Parameter(description = "CRN", required = true, example = "D1974X")
    @PathVariable crn: String,
  ): RiskRoshSummaryDto {
    return riskService.getRoshRiskSummaryByCrn(crn)
  }

  @RequestMapping(path = ["/risks/crn/{crn}/self"], method = [RequestMethod.GET])
  @Operation(description = "Gets rosh to individual for crn")
  @ApiResponses(
    value = [
      ApiResponse(responseCode = "403", description = "Unauthorized"),
      ApiResponse(responseCode = "404", description = "CRN Not Found"),
      ApiResponse(responseCode = "200", description = "OK")
    ]
  )
  @PreAuthorize("hasAnyRole('ROLE_PROBATION', 'ROLE_CRS_PROVIDER') and hasAuthority('SCOPE_read')")
  fun getRiskToSelfByCrn(
    @Parameter(description = "CRN", required = true, example = "D1974X")
    @PathVariable crn: String,
  ): RoshRiskToSelfDto {
    return riskService.getRoshRisksToSelfByCrn(crn)
  }

  @RequestMapping(path = ["/risks/crn/{crn}/other"], method = [RequestMethod.GET])
  @Operation(description = "Gets other rosh risks for crn")
  @ApiResponses(
    value = [
      ApiResponse(responseCode = "403", description = "Unauthorized"),
      ApiResponse(responseCode = "404", description = "CRN Not Found"),
      ApiResponse(responseCode = "200", description = "OK")
    ]
  )
  @PreAuthorize("hasAnyRole('ROLE_PROBATION', 'ROLE_CRS_PROVIDER') and hasAuthority('SCOPE_read')")
  fun getOtherRisksByCrn(
    @Parameter(description = "CRN", required = true, example = "D1974X")
    @PathVariable crn: String,
  ): OtherRoshRisksDto {
    return riskService.getOtherRoshRisk(crn)
  }

  @RequestMapping(path = ["/risks/crn/{crn}"], method = [RequestMethod.GET])
  @Operation(description = "Gets other rosh risks for crn")
  @ApiResponses(
    value = [
      ApiResponse(responseCode = "403", description = "Unauthorized"),
      ApiResponse(responseCode = "404", description = "CRN Not Found"),
      ApiResponse(responseCode = "200", description = "OK")
    ]
  )
  @PreAuthorize("hasAnyRole('ROLE_PROBATION', 'ROLE_CRS_PROVIDER', 'RISK_SUMMARY') and hasAuthority('SCOPE_read')")
  fun getRoshRisksByCrn(
    @Parameter(description = "CRN", required = true, example = "D1974X")
    @PathVariable crn: String,
  ): AllRoshRiskDto {
    return riskService.getRoshRisksByCrn(crn)
  }
}
