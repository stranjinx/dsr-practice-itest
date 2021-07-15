package ru.dsr.itest.rest.controller;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.dsr.itest.db.entity.Variant;
import ru.dsr.itest.db.entity.VariantConfig;
import ru.dsr.itest.rest.Api;
import ru.dsr.itest.rest.dto.VariantDto;
import ru.dsr.itest.rest.response.TestVariantsView;
import ru.dsr.itest.rest.response.VariantView;
import ru.dsr.itest.security.details.AccountDetails;
import ru.dsr.itest.service.VariantService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Api.V1 + "/edit/var")
@RequiredArgsConstructor
public class VariantController {
    private final VariantService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void pushSettings(@AuthenticationPrincipal AccountDetails details,
                             @RequestBody @Valid VariantDto settings) {
        service.pushVariantSettings(details.getId(), settings);
    }

    @GetMapping("all/{testId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TestVariantsView> findAll(@AuthenticationPrincipal AccountDetails details,
                                          @PathVariable Integer testId) {
        return TestVariantsView.from(service.findAll(details.getId(), testId));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VariantDto getVariant(@AuthenticationPrincipal AccountDetails details,
                              @PathVariable Integer id) {
        return VariantDto.from(service.findVariant(details.getId(), id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVariant(@AuthenticationPrincipal AccountDetails details,
                              @PathVariable Integer id) {
        service.deleteVariant(details.getId(), id);
    }
}
