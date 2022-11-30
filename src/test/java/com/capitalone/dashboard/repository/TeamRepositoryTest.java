package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.Feature;
import com.capitalone.dashboard.model.Team;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamRepositoryTest {
    private static Team mockV1Team;
    private static Team mockJiraTeam;
    private static Team mockJiraTeam2;
    private static CollectorItem mockBadItem;
    private static final String generalUseDate = "2015-11-01T00:00:00Z";
    private static final String olderThanGeneralUseDate = "2015-10-30T00:00:00Z";
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static Calendar cal = Calendar.getInstance();
    private static final String maxDateWinner = df.format(new Date());
    private static String maxDateLoser = new String();
    private static final ObjectId jiraCollectorId = new ObjectId();
    private static final ObjectId v1CollectorId = new ObjectId();


    @Mock
    private TeamRepository teamRepo;

    @Mock
    private CollectorItemRepository badItemRepo;

    @BeforeEach
    public void setUp() {
        // Date-time modifications
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -1);
        maxDateLoser = df.format(cal.getTime());

        // VersionOne Mock Scope
        mockV1Team = new Team("", "");
        mockV1Team.setCollectorId(v1CollectorId);
        mockV1Team.setIsDeleted("False");
        mockV1Team.setChangeDate(maxDateLoser);
        mockV1Team.setAssetState("Active");
        mockV1Team.setId(ObjectId.get());
        mockV1Team.setTeamId("Team:129825");
        mockV1Team.setName("Resistance");
        mockV1Team.setEnabled(true);

        // Jira Mock Scope
        // Mock Scope 1
        mockJiraTeam = new Team("", "");
        mockJiraTeam.setCollectorId(jiraCollectorId);
        mockJiraTeam.setIsDeleted("False");
        mockJiraTeam.setChangeDate(maxDateWinner);
        mockJiraTeam.setAssetState("Active");
        mockJiraTeam.setId(ObjectId.get());
        mockJiraTeam.setTeamId("871589423");
        mockJiraTeam.setName("Sith Lords");
        mockJiraTeam.setEnabled(true);

        // Mock Scope 2
        mockJiraTeam2 = new Team("", "");
        mockJiraTeam2.setCollectorId(jiraCollectorId);
        mockJiraTeam2.setIsDeleted("False");
        mockJiraTeam2.setChangeDate(generalUseDate);
        mockJiraTeam2.setAssetState("Active");
        mockJiraTeam2.setId(ObjectId.get());
        mockJiraTeam2.setTeamId("078123416");
        mockJiraTeam2.setName("Jedi Knights");
        mockJiraTeam2.setEnabled(false);

        // Mock Alternative Collector Item
        mockBadItem = new CollectorItem();
        mockBadItem.setCollector(new Collector());
        mockBadItem.setCollectorId(jiraCollectorId);
        mockBadItem.setDescription("THIS SHOULD NOT SHOW UP");
        mockBadItem.setEnabled(true);
        mockBadItem.setId(ObjectId.get());
    }

    @After
    public void tearDown() {
        mockV1Team = null;
        mockJiraTeam = null;
        mockJiraTeam2 = null;
        mockBadItem = null;
        badItemRepo.deleteAll();
        teamRepo.deleteAll();
    }

    @Test
    public void validateConnectivity_HappyPath() {
        teamRepo.save(mockV1Team);
        teamRepo.save(mockJiraTeam);
        teamRepo.save(mockJiraTeam2);

        List<Team> items = new ArrayList<>();
        items.add(mockV1Team);
        Iterable<Team> itr = (Iterable<Team>) items;

        when(teamRepo.findAll()).thenReturn(itr);

        assertTrue("Happy-path MongoDB connectivity validation for the ScopeRepository has failed",
                teamRepo.findAll().iterator().hasNext());
    }

    @Test
    public void testFindTeamCollector_NoCollectorForGivenFilter() {
        teamRepo.save(mockV1Team);
        teamRepo.save(mockJiraTeam);
        teamRepo.save(mockJiraTeam2);

        List<Team> outputList = new ArrayList<>();
        assertEquals("Expected null response did not match actual null response", outputList.size(),
                teamRepo.findByCollectorId(new ObjectId("588fc489bb6280f450f2b647")).size());
    }

    @Test
    public void testGetTeamMaxChangeDate_HappyPath() {

        teamRepo.save(mockV1Team);
        teamRepo.save(mockJiraTeam);
        teamRepo.save(mockJiraTeam2);

        List<Team> outputList = teamRepo.findTopByChangeDateDesc(mockJiraTeam.getCollectorId(), olderThanGeneralUseDate);
        outputList.add(mockJiraTeam);

        Mockito.lenient().when(teamRepo.findTopByChangeDateDesc(mockJiraTeam.getCollectorId(), maxDateLoser)).thenReturn(outputList);

        assertTrue(outputList.size() >0);
        assertEquals(
                "Expected number of enabled team collectors did not match actual number of enabled team collectors",
                mockJiraTeam.getChangeDate(),
                teamRepo
                        .findTopByChangeDateDesc(mockJiraTeam.getCollectorId(), maxDateLoser)
                        .get(0).getChangeDate());
        assertEquals(
                "Expected number of enabled team collectors did not match actual number of enabled team collectors",
                maxDateWinner,
                outputList.get(0).getChangeDate());
    }

    @Test
    public void testGetTeamIdById_HappyPath() {
        teamRepo.save(mockV1Team);
        teamRepo.save(mockJiraTeam);
        teamRepo.save(mockJiraTeam2);

        String id = mockJiraTeam2.getTeamId();

        assertEquals(id,"078123416");
    }

}
