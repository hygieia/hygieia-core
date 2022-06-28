
package com.capitalone.dashboard.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class DashboardCreateTests {  //extends FongoBaseRepositoryTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private CmdbRepository cmdbRepository;

    @Test
    public void createTeamDashboardTest() {
        Component component = new Component("Jay's component");
        component.setOwner("Jay");

        component = componentRepository.save(component);
        System.out.println(component.getId());

        Cmdb configItemApp = new Cmdb();
        configItemApp.setConfigurationItem("ASVTEST");
        configItemApp = cmdbRepository.save(configItemApp);


        Cmdb configItemComp = new Cmdb();
        configItemComp.setConfigurationItem("BAPTEST");
        configItemComp = cmdbRepository.save(configItemComp);


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


        dashboardRepository.save(dashboard);



        for (Dashboard d : dashboardRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))) {
            System.out.println(d.getTitle());
            assertEquals(d.getTitle(), "Jays's Dashboard");
        }

    }
}
