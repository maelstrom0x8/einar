package com.maelstrom.config.security;

import jakarta.validation.constraints.NotNull;

public record AuthenticatedUser(@NotNull String subject, java.util.Map<String, Object> claims) {}
