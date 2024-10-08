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

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.util.MultiValueMap;

import io.ceze.einar.space.domain.model.Space;

public class SpaceFilter {

    Example<Space> example;

    private SpaceFilter(Example<Space> example) {
        this.example = example;
    }

    public static SpaceFilter of(MultiValueMap<String, String> params) {
        Space space  = new Space();
        
        Example<Space> spaceExample = Example.of(space);
        return new SpaceFilter(spaceExample);
    }
}
