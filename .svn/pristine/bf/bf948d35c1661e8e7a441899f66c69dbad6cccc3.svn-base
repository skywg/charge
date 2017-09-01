/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iycharge.server.domain.elastic.repository;

import com.iycharge.server.domain.elastic.document.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends ElasticsearchRepository<Device, String> {
    public Page<Device> findByCityLikeOrNameLikeOrAddressLikeAndGeoPointLike(String city, String name, String address, GeoPoint geopoint, Pageable pageable);

    public Page<Device> findByCityLikeOrNameLikeOrAddressLikeAndGeoPointLikeAndStatus(String city, String name, String address, GeoPoint geopoint, String status, Pageable pageable);
}
