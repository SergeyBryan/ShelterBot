package com.example.shelterbot.controllers;

import com.example.shelterbot.model.Cats;
import com.example.shelterbot.model.Dogs;
import com.example.shelterbot.model.Volunteer;
import com.example.shelterbot.service.CatsService;
import com.example.shelterbot.service.DogsService;
import com.example.shelterbot.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/volunteer")
@Tag(name = "Shelter Bot", description = "CRUD операции для работы с приютом для животных.")
public class VolunteerController {
    private final VolunteerService volunteerService;
    private final CatsService catsService;
    private final DogsService dogsService;

    public VolunteerController(VolunteerService volunteerService, CatsService catsService, DogsService dogsService) {
        this.volunteerService = volunteerService;
        this.catsService = catsService;
        this.dogsService = dogsService;
    }

    @SneakyThrows
    @GetMapping("{id}")
    @Operation(summary = "Отчет.", description = "Посмотреть отчет.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Запрос выполнен."),
            @ApiResponse(responseCode = "400",
                    description = "Некорректный формат запроса."),
            @ApiResponse(responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны.")
    })
    public ResponseEntity<Volunteer> getId(@PathVariable long id) {
        Volunteer volunteer = volunteerService.getById((int) id);
        if (volunteer == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteer);
    }

    @PostMapping("/volunteer/contact_user")
    @Operation(summary = "Связь.", description = "Связаться с пользователем.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Запрос выполнен."),
            @ApiResponse(responseCode = "400",
                    description = "Некорректный формат запроса."),
            @ApiResponse(responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны.")
    })
    public ResponseEntity<Volunteer> addVolunteer(@RequestBody Volunteer volunteer) {
        volunteerService.save(volunteer);
        return ResponseEntity.ok(volunteer);
    }

    @SneakyThrows
    @PostMapping("/extend-trial-period")
    @Operation(summary = "Продлить испытательный срок.", description = "Продливание испытательного срока животного.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Запрос выполнен."),
            @ApiResponse(responseCode = "400",
                    description = "Некорректный формат запроса."),
            @ApiResponse(responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны.")
    })
    public ResponseEntity<String> extendTrialPeriod(@RequestParam long id, @RequestParam int days) {
        volunteerService.getAll();
        return ResponseEntity.ok("Испытательный срок животного успешно продлен на " + days + " дней");
    }

    @SneakyThrows
    @PostMapping("/add-cats")
    @Operation(summary = "Добавление кошки.", description = "Добавление новой кошки в приют.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Запрос выполнен."),
            @ApiResponse(responseCode = "400",
                    description = "Некорректный формат запроса."),
            @ApiResponse(responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны.")
    })
    public ResponseEntity<String> addCats(@RequestBody Cats cats) {
        catsService.save(cats);
        return ResponseEntity.ok("Животное " + cats.getName() + " успешно добавлено в приют");
    }

    @SneakyThrows
    @PostMapping("/add-dogs")
    @Operation(summary = "Добавление собаки.", description = "Добавление новой собаки в приют.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Запрос выполнен."),
            @ApiResponse(responseCode = "400",
                    description = "Некорректный формат запроса."),
            @ApiResponse(responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны.")
    })
    public ResponseEntity<String> addDogs(@RequestBody Dogs dogs) {
        dogsService.save(dogs);
        return ResponseEntity.ok("Животное " + dogs.getName() + " успешно добавлено в приют");
    }
}
