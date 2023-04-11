/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.modules.impl.dashboard.integtests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.setup.PersonaEnumPersistAll;

import domainapp.modules.impl.PetClinicModuleIntegTestAbstract;
import domainapp.modules.impl.dashboard.Dashboard;
import domainapp.modules.impl.dashboard.HomePageProvider;
import domainapp.modules.impl.pets.fixture.Owner_enum;
import domainapp.modules.impl.visits.dom.Visit;
import static org.assertj.core.api.Assertions.assertThat;

public class Dashboard_paid_IntegTest extends PetClinicModuleIntegTestAbstract {

    Dashboard dashboard;

    @Before
    public void setup() {
        // given
        runFixtureScript(new PersonaEnumPersistAll<>(Owner_enum.class));
        dashboard = homePageProvider.dashboard();
    }

    @Test
    public void happy_case() {

        // given
        List<Visit> overdue = dashboard.getOverdue();
        assertThat(overdue).isNotEmpty();

        // when
        wrap(dashboard).paid(overdue);

        // then
        List<Visit> overdueAfter = dashboard.getOverdue();
        assertThat(overdueAfter).isEmpty();

        for (Visit visit : overdue) {
            assertThat(visit.getDiagnosis()).isNotNull();
            assertThat(visit.getPaidOn()).isNotNull();
        }
    }

    @Inject
    HomePageProvider homePageProvider;
}