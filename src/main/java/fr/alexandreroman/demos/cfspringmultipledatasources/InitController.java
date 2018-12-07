/*
 * Copyright (c) 2018 Pivotal Software, Inc.
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

package fr.alexandreroman.demos.cfspringmultipledatasources;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import fr.alexandreroman.demos.cfspringmultipledatasources.person.Person;
import fr.alexandreroman.demos.cfspringmultipledatasources.person.PersonRepository;
import fr.alexandreroman.demos.cfspringmultipledatasources.superhero.Superhero;
import fr.alexandreroman.demos.cfspringmultipledatasources.superhero.SuperheroRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@Slf4j
@RequiredArgsConstructor
public class InitController {
    @NonNull
    private SuperheroRepository superheroRepository;
    @NonNull
    private PersonRepository personRepository;

    @GetMapping(value = "/init", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String init(@RequestParam(value = "count", defaultValue = "10") @Positive int count) {
        log.info("Initializing databases");

        final Faker faker = new Faker();
        for (int i = 0; i < count; ++i) {
            // We create new Superhero and new Person instances,
            // using each JPA repository. These entities are saved
            // into different databases.

            final Superhero superhero = new Superhero();
            superhero.setName(faker.superhero().name());
            superheroRepository.save(superhero);

            final Person person = new Person();
            final Name name = faker.name();
            person.setFirstName(name.firstName());
            person.setLastName(name.lastName());
            personRepository.save(person);
        }

        return "Initialized " + count + " people and " + count + " superheroes";
    }

    @GetMapping(value = "/clear", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String clear() {
        log.info("Deleting all people & superheroes in database");
        personRepository.deleteAll();
        superheroRepository.deleteAll();
        return "Deleted all people & superheroes";
    }
}
