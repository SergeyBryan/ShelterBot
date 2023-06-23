package com.example.shelterbot.controllers;

import com.example.shelterbot.model.Cats;
import com.example.shelterbot.model.Dogs;
import com.example.shelterbot.model.Report;
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

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

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
    public ResponseEntity<Report> getReportByUserId(@PathVariable long id) {
        return ResponseEntity.ok(volunteerService.getReportByUserId((int) id));
    }

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
        volunteerService.extendTrialPeriod(days, id);
        return ResponseEntity.ok("Испытательный срок животного успешно продлен на " + days + " дней");
    }

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
        volunteerService.addCatInShelter(cats);
        return ResponseEntity.ok("Животное " + cats.getName() + " успешно добавлено в приют");
    }

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
        volunteerService.addDogInShelter(dogs);
        return ResponseEntity.ok("Животное " + dogs.getName() + " успешно добавлено в приют");
    }
}
