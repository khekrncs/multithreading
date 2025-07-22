/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.loanapp;

import com.cs.multithreading.loanapp.functions.KYCVerificationCallable;
import com.cs.multithreading.loanapp.functions.LoanAppFormPosting;
import com.cs.multithreading.loanapp.functions.UnderwritingCallable;
import com.cs.multithreading.loanapp.model.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LoanApprovalService {

    private final ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

    public LoanDecisionResult processLoanApplication(LoanInput loanInput) {
        System.out.printf("🏦 Starting loan decision workflow for %s%n", loanInput.name());

        try {

            // Step 1: Post loan application
            System.out.println("📝 Step 1: Submitting loan application...");
            Future<LoanOutput> loanPostingFuture = virtualExecutor.submit(
                    new LoanAppFormPosting(loanInput)
            );

            // Wait for loan posting to complete and get application ID
            LoanOutput loanResult = loanPostingFuture.get();
            String applicationId = loanResult.id();

            if (applicationId == null || !"accepted".equals(loanResult.status())) {
                System.err.printf("❌ Failed to create loan application for %s%n", loanInput.name());
                return LoanDecisionResult.failed("Failed to create loan application");
            }

            System.out.printf("📋 Application created with ID: %s%n", applicationId);

            // Step 2: Run underwriting check
            System.out.println("🔍 Step 2: Running underwriting check...");
            Future<UnderwritingStatus> underwritingFuture = virtualExecutor.submit(
                    new UnderwritingCallable(applicationId)
            );

            UnderwritingStatus underwritingResult = underwritingFuture.get();
            System.out.printf("📊 Underwriting result for %s: %s%n",
                    loanInput.name(), underwritingResult.status());

            // Step 3: If underwriting approved, run KYC verification
            if ("approved".equals(underwritingResult.status())) {
                System.out.println("🆔 Step 3: Running KYC verification...");
                Future<KYCStatus> kycFuture = virtualExecutor.submit(
                        new KYCVerificationCallable(applicationId)
                );

                KYCStatus kycResult = kycFuture.get();
                System.out.printf("🆔 KYC result for %s: %s%n",
                        loanInput.name(), kycResult.status());

                if ("approved".equals(kycResult.status())) {
                    System.out.printf("🎉 FINAL DECISION: APPROVED for %s%n", loanInput.name());
                    return LoanDecisionResult.approved(applicationId, loanInput.name(),
                            underwritingResult.status(), kycResult.status());
                } else {
                    System.out.printf("❌ FINAL DECISION: REJECTED for %s (KYC failed)%n", loanInput.name());
                    return LoanDecisionResult.rejectedKYC(applicationId, loanInput.name(),
                            underwritingResult.status(), kycResult.status());
                }
            } else {
                System.out.printf("❌ FINAL DECISION: REJECTED for %s (Underwriting failed)%n", loanInput.name());
                return LoanDecisionResult.rejectedUnderwriting(applicationId, loanInput.name(),
                        underwritingResult.status());
            }

        } catch (Exception e) {
            System.err.printf("❌ Error in loan decision workflow for %s: %s%n",
                    loanInput.name(), e.getMessage());
            return LoanDecisionResult.error("System error: " + e.getMessage());
        }
    }

    private void printDecisionResult(LoanDecisionResult result) {
        System.out.printf("  👤 Applicant: %s%n", result.applicantName());
        System.out.printf("  🆔 Application ID: %s%n", result.applicationId());
        System.out.printf("  📊 Underwriting: %s%n", result.underwritingStatus());
        System.out.printf("  🆔 KYC: %s%n", result.kycStatus());
        System.out.printf("  🎯 FINAL STATUS: %s%n", result.finalStatus());
        System.out.printf("  💭 Reason: %s%n", result.reason());
        System.out.println("-" + "-".repeat(80));
    }

    private void shutdownExecutor() {
        virtualExecutor.shutdown();
    }

    // Demo method to test the workflow
    public static void main(String[] args) {
        LoanApprovalService loanApprovalService = new LoanApprovalService();
        try {
            LoanInput singleApplication = new LoanInput("Arjun Mehta", "85000");
            LoanDecisionResult singleResult = loanApprovalService.processLoanApplication(singleApplication);
            loanApprovalService.printDecisionResult(singleResult);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            loanApprovalService.shutdownExecutor();
        }
    }
}
