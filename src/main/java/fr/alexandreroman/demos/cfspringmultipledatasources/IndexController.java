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

import fr.alexandreroman.demos.cfspringmultipledatasources.person.Person;
import fr.alexandreroman.demos.cfspringmultipledatasources.person.PersonRepository;
import fr.alexandreroman.demos.cfspringmultipledatasources.superhero.Superhero;
import fr.alexandreroman.demos.cfspringmultipledatasources.superhero.SuperheroRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.PrintWriter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    @NonNull
    private PersonRepository personRepository;
    @NonNull
    private SuperheroRepository superheroRepository;

    @GetMapping("/")
    public void index(PrintWriter out) {
        final List<Person> people =
                personRepository.findAll(Sort.by("lastName", "firstName").ascending());
        if (people.isEmpty()) {
            out.println("Found no people in database");
        } else {
            out.println("Found people:");
            for (final Person person : people) {
                out.println("  - " + person.getFirstName() + " " + person.getLastName());
            }
        }

        out.println("--------");

        final List<Superhero> superheroes = superheroRepository.findAll(Sort.by("name").ascending());
        if (superheroes.isEmpty()) {
            out.println("Found no superheroes in database");
        } else {
            out.println("Found superheroes:");
            for (final Superhero superhero : superheroes) {
                out.println("  - " + superhero.getName());
            }
        }
    }
}
