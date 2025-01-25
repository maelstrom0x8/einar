package com.maelstrom.einar.supplier.adapter.inbound.web.dto;

import java.util.Set;

public record RegisterSupplierRequest(String name, String email, String phone, Set<String> itemIds) {}
