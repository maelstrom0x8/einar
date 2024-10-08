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
package io.ceze.einar.space.domain.service;

import io.ceze.einar.space.domain.model.Space;
import io.ceze.einar.space.domain.model.dto.NewSpaceRequest;
import io.ceze.einar.space.domain.repository.SpaceRepository;
import io.ceze.einar.user.domain.dto.LocationInfo;
import io.ceze.einar.user.domain.model.Location;
import io.ceze.einar.user.domain.model.User;
import io.ceze.einar.user.domain.repository.LocationRepository;
import io.ceze.einar.user.domain.service.UserService;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpaceService {

  private static final Logger log = LoggerFactory.getLogger(SpaceService.class);

  private final UserService userService;
  private final SpaceRepository spaceRepository;
  private final LocationRepository locationRepository;

  public SpaceService(
      UserService userService,
      SpaceRepository spaceRepository,
      LocationRepository locationRepository) {
    this.userService = userService;
    this.spaceRepository = spaceRepository;
    this.locationRepository = locationRepository;
  }

  @Transactional
  @PreAuthorize("hasAuthorithy('spaces:create')")
  public void addSpace(User user, NewSpaceRequest request) {
    log.info("Creating new space");
    Location location = LocationInfo.from(request.locationInfo());
    locationRepository.save(location);

    Space space =
        new Space(request.descripition(), location, user, request.pricing(), request.type());
    spaceRepository.save(space);
  }

  public Space getSpaceById(Long id) {
    return spaceRepository.findById(id).orElseThrow();
  }

  public List<Space> filteredSearch(SpaceFilter spaceFilter) {
    return spaceRepository.findAll(spaceFilter.example, PageRequest.ofSize(10)).toList();
  }
}
