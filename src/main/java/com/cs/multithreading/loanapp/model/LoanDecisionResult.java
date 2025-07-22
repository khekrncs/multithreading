/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.loanapp.model;

public record LoanDecisionResult(
        String applicationId,
        String finalStatus,
        String underwritingStatus,
        String kycStatus,
        String reason,
        String applicantName
) {
    
    public static LoanDecisionResult failed(String reason) {
        return new LoanDecisionResult(null, "FAILED", "N/A", "N/A", reason, null);
    }
    
    public static LoanDecisionResult error(String reason) {
        return new LoanDecisionResult(null, "ERROR", "N/A", "N/A", reason, null);
    }
    
    public static LoanDecisionResult approved(String applicationId, String applicantName, 
                                            String underwritingStatus, String kycStatus) {
        return new LoanDecisionResult(applicationId, "APPROVED", underwritingStatus, 
                                    kycStatus, "All checks passed successfully", applicantName);
    }
    
    public static LoanDecisionResult rejectedUnderwriting(String applicationId, String applicantName, 
                                                         String underwritingStatus) {
        return new LoanDecisionResult(applicationId, "REJECTED", underwritingStatus, 
                                    "N/A", "Underwriting check failed", applicantName);
    }
    
    public static LoanDecisionResult rejectedKYC(String applicationId, String applicantName, 
                                                String underwritingStatus, String kycStatus) {
        return new LoanDecisionResult(applicationId, "REJECTED", underwritingStatus, 
                                    kycStatus, "KYC verification failed", applicantName);
    }
}
