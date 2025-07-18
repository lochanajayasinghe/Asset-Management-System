package com.example.AMS.controller;

import com.example.AMS.model.AssetUser;
import com.example.AMS.service.L_AssetUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class L_UserHistoryController {
    private final L_AssetUserService assetUserService;

    public L_UserHistoryController(L_AssetUserService assetUserService) {
        this.assetUserService = assetUserService;
    }

    @GetMapping("/user-history")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public String getUserHistory(Model model,
                                @RequestParam(required = false) String userName,
                                @RequestParam(required = false) String assetId,
                                Authentication authentication) {
        List<AssetUser> userHistories;
        
        if (userName != null && !userName.isEmpty()) {
            userHistories = assetUserService.getUserHistoryByUserName(userName);
        } else if (assetId != null && !assetId.isEmpty()) {
            userHistories = assetUserService.getAssetHistory(assetId);
        } else {
            userHistories = assetUserService.getAllUserHistories();
        }
        
        // Check if the user has only ROLE_USER (not other higher roles)
        boolean isRegularUser = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_USER"::equals) &&
                authentication.getAuthorities().size() == 1;
        
        model.addAttribute("userHistories", userHistories);
        model.addAttribute("isRegularUser", isRegularUser);
        return "UserHistory/UserHistory";
    }

    @GetMapping("/user-history/view/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public String viewHistoryRecord(@PathVariable Long id, Model model) {
        AssetUser history = assetUserService.getUserHistoryById(id);
        model.addAttribute("history", history);
        return "UserHistory/ViewHistory";
    }

}

