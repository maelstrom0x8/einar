/*
 * Copyright (C) 2024 Emmanuel Godwin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ceze.einar.space.web;

import io.ceze.config.security.Authenticated;
import io.ceze.einar.space.domain.model.Space;
import io.ceze.einar.space.domain.model.dto.NewSpaceRequest;
import io.ceze.einar.space.domain.service.SpaceFilter;
import io.ceze.einar.space.domain.service.SpaceService;
import io.ceze.einar.user.domain.model.User;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/spaces")
public class SpaceController {

  private static final Logger log = LoggerFactory.getLogger(SpaceController.class);

  private final SpaceService spaceService;

  public SpaceController(SpaceService spaceService) {
    this.spaceService = spaceService;
  }

  @PostMapping
  public void registerNewSpace(@Authenticated User user, @RequestBody NewSpaceRequest request) {
    spaceService.addSpace(user, request);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Space> fetchSpaceById(@PathVariable("id") Long spaceId) {
    try {
      Space space = spaceService.getSpaceById(spaceId);
      return ResponseEntity.ok().body(space);
    } catch (Exception e) {
      log.error("Space with ID: {} does not exist", spaceId);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public List<Space> searchSpaces(@RequestParam MultiValueMap<String, String> params) {
    return spaceService.filteredSearch(SpaceFilter.of(params));
  }

  
}
