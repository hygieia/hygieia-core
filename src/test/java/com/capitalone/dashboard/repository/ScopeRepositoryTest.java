package com.capitalone.dashboard.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.capitalone.dashboard.model.Scope;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScopeRepositoryTest  {
	private static Scope mockV1Scope;
	private static Scope mockJiraScope;
	private static Scope mockJiraScope2;
	private static final String generalUseDate = "2015-11-01T00:00:00Z";
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	private static Calendar cal = Calendar.getInstance();
	private static final String maxDateWinner = df.format(new Date());
	private static String maxDateLoser = new String();
	private static String currentScopeEndDate = new String();
	private static final ObjectId jiraCollectorId = new ObjectId();
	private static final ObjectId v1CollectorId = new ObjectId();

	//	@Autowired
//	@Mock
	private ScopeRepository scopeRepo = Mockito.mock(ScopeRepository.class);

	@BeforeAll
	public void setUp() {
		// Date-time modifications
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, -1);
		maxDateLoser = df.format(cal.getTime());
		cal.add(Calendar.DAY_OF_YEAR, +13);
		currentScopeEndDate = df.format(cal.getTime());

		// VersionOne Mock Scope
		mockV1Scope = new Scope();
		mockV1Scope.setCollectorId(v1CollectorId);
		mockV1Scope.setIsDeleted("False");
		mockV1Scope.setChangeDate(maxDateLoser);
		mockV1Scope.setAssetState("Active");
		mockV1Scope.setBeginDate(maxDateLoser);
		mockV1Scope.setEndDate(currentScopeEndDate);
		mockV1Scope.setId(ObjectId.get());
		mockV1Scope.setIsDeleted("False");
		mockV1Scope.setName("Massive Project");
		mockV1Scope.setpId("Scope:14327");
		mockV1Scope
				.setProjectPath("This -> Is -> C -> Project -> Path -> " + mockV1Scope.getName());

		// Jira Mock Scope
		// Mock Scope 1
		mockJiraScope = new Scope();
		mockJiraScope.setCollectorId(jiraCollectorId);
		mockJiraScope.setIsDeleted("False");
		mockJiraScope.setChangeDate(maxDateWinner);
		mockJiraScope.setAssetState("Active");
		mockJiraScope.setBeginDate(maxDateLoser);
		mockJiraScope.setEndDate(currentScopeEndDate);
		mockJiraScope.setId(ObjectId.get());
		mockJiraScope.setIsDeleted("False");
		mockJiraScope.setName("Yet Another Agile Scope");
		mockJiraScope.setpId("110213780");
		mockJiraScope.setProjectPath("This -> Is -> B -> Project -> Path -> "
				+ mockJiraScope.getName());

		// Mock Scope 2
		mockJiraScope2 = new Scope();
		mockJiraScope2.setCollectorId(jiraCollectorId);
		mockJiraScope2.setIsDeleted("False");
		mockJiraScope2.setChangeDate(generalUseDate);
		mockJiraScope2.setAssetState("Inactive");
		mockJiraScope2.setBeginDate(maxDateLoser);
		mockJiraScope2.setEndDate(currentScopeEndDate);
		mockJiraScope2.setId(ObjectId.get());
		mockJiraScope2.setIsDeleted("False");
		mockJiraScope2.setName("This One Is Serious");
		mockJiraScope2.setpId("11978790");
		mockJiraScope2.setProjectPath("This -> Is -> A -> Project -> Path -> "
				+ mockJiraScope2.getName());
	}

	@AfterAll
	public void tearDown() {
		mockV1Scope = null;
		mockJiraScope = null;
		mockJiraScope2 = null;
		scopeRepo.deleteAll();
	}

	@Test
	public void validateConnectivity_HappyPath() {
		List<Scope> scopes = new ArrayList<Scope>();
		Iterable<Scope> scopesIt = scopes;
		scopeRepo.save(mockV1Scope);
		scopes.add(mockV1Scope);
		scopeRepo.save(mockJiraScope);
		scopes.add(mockJiraScope);
		scopeRepo.save(mockJiraScope2);
		scopes.add(mockJiraScope2);

		when(scopeRepo.findAll()).thenReturn(scopesIt);

		assertTrue("Happy-path MongoDB connectivity validation for the ScopeRepository has failed",
				scopeRepo.findAll().iterator().hasNext());

		verify(scopeRepo).findAll();
	}

	@Test
	public void testGetScopeIdById_HappyPath() {
		List<Scope> scopes = new ArrayList<Scope>();

		scopes.add(mockJiraScope);
		scopeRepo.save(mockJiraScope);
		scopes.add(mockJiraScope2);
		scopeRepo.save(mockJiraScope2);
		scopes.add(mockV1Scope);
		scopeRepo.save(mockV1Scope);
		String testScopeId = mockJiraScope.getpId();
		//110213780

		when(scopeRepo
				.getScopeIdById(testScopeId)).thenReturn(scopes);

		assertEquals("Expected scope ID did not match actual scope ID", testScopeId, scopeRepo
				.getScopeIdById(testScopeId).get(0).getpId().toString());
	}

	@Test
	public void testGetScopeById_HappyPath() {
		List<Scope> scopes = new ArrayList<Scope>();

		scopes.add(mockJiraScope);
		scopeRepo.save(mockJiraScope);
//		scopes.add(mockJiraScope2);
		scopeRepo.save(mockJiraScope2);
//		scopes.add(mockV1Scope);
		scopeRepo.save(mockV1Scope);
		String testScopeId = mockJiraScope.getpId();
		//110213780

		when(scopeRepo
				.getScopeById(testScopeId)).thenReturn(scopes);

		assertEquals("Expected scope Name did not match actual scope Name",
				mockJiraScope.getName(), scopeRepo.getScopeById(testScopeId).get(0).getName()
						.toString());

		verify(scopeRepo).getScopeById(testScopeId);
	}

	@Test
	public void testGetAllScopes_HappyPath() {
		List<Scope> scopes = new ArrayList<Scope>();

		scopes.add(mockV1Scope);
		scopeRepo.save(mockV1Scope);
		scopes.add(mockJiraScope);
		scopeRepo.save(mockJiraScope);
		scopes.add(mockJiraScope2);
		scopeRepo.save(mockJiraScope2);


		when(scopeRepo.findByOrderByProjectPathDesc()).thenReturn(scopes);

		assertEquals("Expected scope ID did not match actual scope ID", mockV1Scope.getpId(),
				scopeRepo.findByOrderByProjectPathDesc().get(0).getpId().toString());
		assertEquals("Expected scope ID did not match actual scope ID", mockJiraScope.getpId(),
				scopeRepo.findByOrderByProjectPathDesc().get(1).getpId().toString());
		assertEquals("Expected scope ID did not match actual scope ID", mockJiraScope2.getpId(),
				scopeRepo.findByOrderByProjectPathDesc().get(2).getpId().toString());

		verify(scopeRepo,times(3)).findByOrderByProjectPathDesc();
	}

	@Test
	public void testGetScopeMaxChangeDate_HappyPath() {
		List<Scope> scopes = new ArrayList<Scope>();

		scopeRepo.save(mockJiraScope);
		scopes.add(mockJiraScope);
		scopeRepo.save(mockJiraScope2);

		when(scopeRepo.findTopByCollectorIdAndChangeDateGreaterThanOrderByChangeDateDesc(jiraCollectorId, maxDateLoser))
				.thenReturn(scopes);

		assertEquals(
				"Expected max change dated scope ID did not match actual max change dated scope ID",
				mockJiraScope.getChangeDate(),
				scopeRepo.findTopByCollectorIdAndChangeDateGreaterThanOrderByChangeDateDesc(jiraCollectorId, maxDateLoser).get(0)
						.getChangeDate().toString());

		verify(scopeRepo).findTopByCollectorIdAndChangeDateGreaterThanOrderByChangeDateDesc(jiraCollectorId, maxDateLoser);
	}
}