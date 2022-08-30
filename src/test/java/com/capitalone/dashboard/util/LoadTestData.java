package com.capitalone.dashboard.util;

import com.capitalone.dashboard.model.*;
import com.capitalone.dashboard.repository.*;
import com.capitalone.dashboard.testutil.GsonUtil;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

public class LoadTestData {

    public static void loadDashBoard(DashboardRepository dashboardRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./dashboard/dashboard.json"));
        List<Dashboard> dashboards = gson.fromJson(json, new TypeToken<List<Dashboard>>(){}.getType());
        dashboardRepository.saveAll(dashboards);
    }

    public static void loadCollector (CollectorRepository collectorRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./collectors/coll.json"));
        List<Collector> collector = gson.fromJson(json, new TypeToken<List<Collector>>(){}.getType());
        collectorRepository.saveAll(collector);
    }

    public static void loadComponent(ComponentRepository componentRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./component/component.json"));
        List<Component> components = gson.fromJson(json, new TypeToken<List<Component>>(){}.getType());
        componentRepository.saveAll(components);
    }

    public static void loadCollectorItems(CollectorItemRepository collectorItemRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./collector_items/items.json"));
        List<CollectorItem> collectorItem = gson.fromJson(json, new TypeToken<List<CollectorItem>>(){}.getType());
        collectorItemRepository.saveAll(collectorItem);
    }

    public static void loadAuditReports(AuditReportRepository auditReportRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./audit_reports/audit_reports.json"));
        List<AuditReport> auditReports = gson.fromJson(json, new TypeToken<List<AuditReport>>(){}.getType());
        auditReportRepository.saveAll(auditReports);
    }

    public static void loadSSCRequests(CodeQualityRepository codeQualityRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./securityscan/securityscan.json"));
        List<CodeQuality> ssa = gson.fromJson(json, new TypeToken<List<CodeQuality>>(){}.getType());
        codeQualityRepository.saveAll(ssa);
    }

    public static void loadLibraryPolicy(LibraryPolicyResultsRepository libraryPolicyResultsRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./librarypolicy/librarypolicy.json"));
        List<LibraryPolicyResult> ssa = gson.fromJson(json, new TypeToken<List<LibraryPolicyResult>>() {}.getType());
        libraryPolicyResultsRepository.saveAll(ssa);
    }
    public static void loadTestResults(TestResultRepository testResultRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./test_results/test_results.json"));
        List<TestResult> testResults = gson.fromJson(json, new TypeToken<List<TestResult>>(){}.getType());
        testResultRepository.saveAll(testResults);
    }

    public static void loadCodeQuality(CodeQualityRepository codeQualityRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./codequality/codequality.json"));
        List<CodeQuality> codeQuality = gson.fromJson(json, new TypeToken<List<CodeQuality>>(){}.getType());
        codeQualityRepository.saveAll(codeQuality);
    }

    public static void loadBuilds(BuildRepository buildRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./builds/builds.json"));
        List<Build> builds = gson.fromJson(json, new TypeToken<List<Build>>(){}.getType());
        buildRepository.saveAll(builds);
    }

    public static void loadFeatureFlags(FeatureFlagRepository featureFlagRepository) throws IOException {
        Gson gson = GsonUtil.getGson();
        String json = IOUtils.toString(Resources.getResource("./feature_flags/feature_flags.json"));
        List<FeatureFlag> featureFlags = gson.fromJson(json, new TypeToken<List<FeatureFlag>>(){}.getType());
        featureFlagRepository.saveAll(featureFlags);
    }

}
