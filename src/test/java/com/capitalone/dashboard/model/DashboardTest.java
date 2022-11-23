package com.capitalone.dashboard.model;

import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.util.PipelineUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DashboardTest {

    @Test
    public void findEnvironmentMappings() throws HygieiaException {
        Dashboard dashboard = makeTeamDashboard("template", "title", "appName","" ,"ASVTEST","BAPTEST", "comp1", "comp2");
        dashboard.getWidgets().add(makePipelineWidget("DEV", "QA", null, null, "PROD"));
        Widget buildWidget = new Widget();
        buildWidget.setName("build");
        dashboard.getWidgets().add(buildWidget);

        Widget commitWidget = new Widget();
        commitWidget.setName("repo");
        dashboard.getWidgets().add(commitWidget);

        Map<PipelineStage, String> expected = new HashMap<>();
        expected.put(PipelineStage.valueOf("COMMIT"), "Commit");
        expected.put(PipelineStage.valueOf("BUILD"), "Build");
        expected.put(PipelineStage.valueOf("DEV"), "DEV");
        expected.put(PipelineStage.valueOf("QA"), "QA");
        expected.put(PipelineStage.valueOf("PROD"), "PROD");

        Map<PipelineStage, String> actual = PipelineUtils.getStageToEnvironmentNameMap(dashboard);
        assertEquals(expected, actual);
    }

    @Test
    public void findEnvironmentMappings_no_mappings_configured() throws HygieiaException {
        Dashboard dashboard = makeTeamDashboard("template", "title", "appName","","ASVTEST", "BAPTEST","comp1", "comp2");
        Map<PipelineStage, String> expected = new HashMap<>();

        Map<PipelineStage, String> actual = PipelineUtils.getStageToEnvironmentNameMap(dashboard);
        assertEquals(expected, actual);
    }

    private Dashboard makeTeamDashboard(String template, String title, String appName, String owner, String configItemAppName, String configItemComponentName, String... compNames) {
        Application app = new Application(appName);
        for (String compName : compNames) {
            app.addComponent(new Component(compName));
        }
        List<String> activeWidgets = new ArrayList<>();
        List<Owner> owners = new ArrayList<Owner>();
        owners.add(new Owner("owner", AuthType.STANDARD));
        Dashboard dashboard = new Dashboard(template, title, app, owners, DashboardType.Team,configItemAppName, configItemComponentName, activeWidgets, false, ScoreDisplayType.HEADER);
        return dashboard;
    }

    private Widget makePipelineWidget(String devName, String qaName, String intName, String perfName, String prodName){
        Widget pipelineWidget = new Widget();
        pipelineWidget.setName("pipeline");
        Map<String, String> environmentMap = new HashMap<>();

        if(devName != null){
            environmentMap.put("DEV", devName);
        }
        if(qaName != null) {
            environmentMap.put("QA", qaName);
        }
        if(intName != null) {
            environmentMap.put("INT", intName);
        }
        if(perfName != null) {
            environmentMap.put("PERF", perfName);
        }
        if(prodName != null) {
            environmentMap.put("PROD", prodName);
        }

        pipelineWidget.getOptions().put("mappings", environmentMap);
        return pipelineWidget;
    }
}
