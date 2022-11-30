package com.capitalone.dashboard.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capitalone.dashboard.model.Template;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TemplateRepositoryTest {


    private static Template mockTemplate;

    @Mock
    private TemplateRepository templateRepository;

    @BeforeAll
    public void setUp() {
        mockTemplate = new Template("template1", getWidgetsAndOrder(), getWidgetsAndOrder());
    }

    @AfterAll
    public void tearDown() {
        mockTemplate = null;
        templateRepository.deleteAll();
    }

    @Test
    public void validate_save() {
        //"Happy-path MongoDB connectivity validation for the FeatureRepository has failed"
        templateRepository.save(mockTemplate);
        Iterable<Template> items = templateRepository.findAll();
        assertFalse(
                templateRepository.findAll().iterator().hasNext());
    }

    @Test
    public void validate_get() {
        templateRepository.save(mockTemplate);
        when(templateRepository.findByTemplate("template1")).thenReturn(mockTemplate);


        Template actual = templateRepository.findByTemplate("template1");
        assertEquals(actual.getTemplate(), mockTemplate.getTemplate());
        verify(templateRepository).findByTemplate("template1");
    }

    @Test
    public void validate_delete() {
        ObjectId templateId = ObjectId.get();
        mockTemplate.setId(templateId);
        templateRepository.save(mockTemplate);
        templateRepository.deleteById(templateId);
        Template actual = templateRepository.findByTemplate("template1");
        assertNull(actual);
    }


    private List<String> getWidgetsAndOrder() {
        List<String> widgets = new ArrayList<>();
        widgets.add("Build");
        widgets.add("CodeAnalysis");
        return widgets;
    }
}