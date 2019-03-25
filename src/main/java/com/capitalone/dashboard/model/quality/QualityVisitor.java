package com.capitalone.dashboard.model.quality;

public interface QualityVisitor<T> {

    T produceResult();

    void visit(JunitXmlReport junitXmlReport);

    void visit(FindBugsXmlReport findBugsXmlReport);

    void visit(JacocoXmlReport jacocoXmlReport);

    void visit(PmdReport pmdReport);

    void visit(CheckstyleReport checkstyleReport);

    void visit(MochaJsSpecReport mochaJsSpecReport);

    void visit(CucumberJsonReport cucumberJsonReport);
}
