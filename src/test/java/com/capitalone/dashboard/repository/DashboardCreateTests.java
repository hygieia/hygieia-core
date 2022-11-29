
package com.capitalone.dashboard.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.capitalone.dashboard.model.Application;
import com.capitalone.dashboard.model.AuthType;
import com.capitalone.dashboard.model.Cmdb;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.model.Dashboard;
import com.capitalone.dashboard.model.DashboardType;
import com.capitalone.dashboard.model.Owner;
import com.capitalone.dashboard.model.ScoreDisplayType;
import com.capitalone.dashboard.model.Widget;

@ExtendWith(MockitoExtension.class)
public class DashboardCreateTests {

    @Mock
    private DashboardRepository dashboardRepository;

    @Mock
    private ComponentRepository componentRepository;

    @Mock
    private CmdbRepository cmdbRepository;

    @Test
    public void createTeamDashboardTest() {

        Component component = new Component("Jay's component");
        component.setOwner("Jay");

        when(componentRepository.save(component)).thenReturn(component);
        component = componentRepository.save(component);
        verify(componentRepository).save(component);

        Cmdb configItemApp = new Cmdb();
        configItemApp.setConfigurationItem("ASVTEST");

        when(cmdbRepository.save(configItemApp)).thenReturn(configItemApp);
        configItemApp = cmdbRepository.save(configItemApp);
        verify(cmdbRepository).save(configItemApp);

        Cmdb configItemComp = new Cmdb();
        configItemComp.setConfigurationItem("BAPTEST");

        when(cmdbRepository.save(configItemComp)).thenReturn(configItemComp);
        configItemComp = cmdbRepository.save(configItemComp);
        verify(cmdbRepository).save(configItemComp);


        Application application = new Application("Jay's App", component);

        List<String> activeWidgets = new ArrayList<>();
        List<Owner> owners = new ArrayList<Owner>();
        owners.add(new Owner("owner", AuthType.STANDARD));
        Dashboard dashboard = new Dashboard("Topo", "Jays's Dashboard", application, owners, DashboardType.Team,  configItemApp.getConfigurationItem(), configItemComp.getConfigurationItem(), activeWidgets, false, ScoreDisplayType.HEADER);

        Widget build = new Widget();
        build.setName("build");
        build.getOptions().put("color", "red");
        build.getOptions().put("items", new String[] { "item 1", "item 2"});
        dashboard.getWidgets().add(build);

        Widget scm = new Widget();
        scm.setName("scm");
        scm.getOptions().put("enabled", true);
        scm.getOptions().put("foo", "bar");
        scm.getOptions().put("threshold", 10);
        dashboard.getWidgets().add(scm);
        when(dashboardRepository.save(dashboard)).thenReturn(dashboard);


        dashboardRepository.save(dashboard);
        verify(dashboardRepository).save(dashboard);

        for (Dashboard d : dashboardRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))) {
            System.out.println(d.getTitle());
            assertEquals(d.getTitle(), "Jays's Dashboard");
        }

    }
}
