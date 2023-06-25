package com.example.shelterbot.controllers;

import com.example.shelterbot.model.Cats;
import com.example.shelterbot.model.Dogs;
import com.example.shelterbot.model.Report;
import com.example.shelterbot.model.enums.PetType;
import com.example.shelterbot.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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

    @PutMapping("/extend-trial-period")
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

    @PutMapping("/add-pet-to-owner")
    @Operation(
            summary = "Привязать питомца к усыновителю",
            description = "Привязать питомца к усыновителю",
            parameters = {
                    @Parameter(
                            name = "dogOrCat",
                            description = "Необходимо указать собаку или кошку забрал усыновитель в формате Dog/Cat",
                            example = "Dog/Cat"
                    ),
                    @Parameter(
                            name = "userid",
                            description = "Необходимо указать айди пользователя к которому прикрепляется питомец",
                            example = "1"
                    ),
                    @Parameter(
                            name = "petid",
                            description = "Необходимо указать айди питомца который прикрепляется к пользователю",
                            example = "1"
                    )
            })
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Запрос выполнен."),
                    @ApiResponse(responseCode = "400",
                            description = "Некорректный формат запроса."),
                    @ApiResponse(responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны.")
            })
    public ResponseEntity<String> addPetToOwner(@RequestParam long userid,
                                                @RequestParam long petid,
                                                @RequestParam PetType dogOrCat) {

        volunteerService.addPetToOwner(petid, dogOrCat.toString(), userid);
        return ResponseEntity.ok("Питомец успешно закреплен за пользоватлем ");
    }

}
