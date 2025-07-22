/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.loanapp.model;

import com.cs.multithreading.loanapp.utils.JsonUtils;

public record LoanInput(String name, String amount) {

    @Override
    public String toString() {
        // Using JsonUtils for consistent JSON formatting
        return JsonUtils.toJson(this);
    }
    
    // Additional utility methods
    public static LoanInput fromJsonString(String json) {
        return JsonUtils.fromJson(json, LoanInput.class);
    }
    
    public String toPrettyString() {
        return JsonUtils.toPrettyJson(this);
    }
}
