package com.maelstrom.einar.inventory.adapter.web.resource;

import com.maelstrom.einar.inventory.application.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;


@ActiveProfiles("test")
@WebMvcTest(controllers = InventoryResource.class)
@AutoConfigureMockMvc
class InventoryResourceTest
{
	@Autowired
	private MockMvcTester mvc;

	@MockitoBean
	private InventoryService inventoryService;

}
