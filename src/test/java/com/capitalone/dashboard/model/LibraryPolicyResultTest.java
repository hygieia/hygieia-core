package com.capitalone.dashboard.model;

import org.junit.Test;

import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.*;

public class LibraryPolicyResultTest {

    @Test
    public void addThreat() {
        LibraryPolicyResult result = new LibraryPolicyResult();
        result.addThreat(LibraryPolicyType.License, LibraryPolicyThreatLevel.Critical, LibraryPolicyThreatDisposition.Open, "Open:Review Requested", "component1","0","7");

        assertEquals(result.getThreats().size(), 1);
        assertEquals(result.getThreats().keySet().size(), 1);
        assertEquals(result.getThreats().values().size(), 1);
        assertEquals(result.getThreats().keySet().iterator().next(), LibraryPolicyType.License);
        Set<LibraryPolicyResult.Threat> threats = result.getThreats().get(LibraryPolicyType.License);
        LibraryPolicyResult.Threat threat = threats.iterator().next();
        assertEquals(threat.getCount(), 1);
        assertEquals(threat.getLevel(), LibraryPolicyThreatLevel.Critical);
        assertEquals(threat.getComponents().iterator().next(), "component1##Open##0##Open:Review Requested##7");
        assertEquals(threat.getDispositionCounts().size(), 1);
        assertEquals(threat.getDispositionCounts().get(LibraryPolicyThreatDisposition.Open).intValue(), 1);


        result.addThreat(LibraryPolicyType.License, LibraryPolicyThreatLevel.Critical, LibraryPolicyThreatDisposition.Open, "Open", "component2","0","7");
        assertEquals(result.getThreats().size(), 1);
        assertEquals(result.getThreats().keySet().size(), 1);
        assertEquals(result.getThreats().values().size(), 1);
        assertEquals(result.getThreats().keySet().iterator().next(), LibraryPolicyType.License);
        threats = result.getThreats().get(LibraryPolicyType.License);
        assertEquals(threats.size(), 1);
        threat = threats.iterator().next();
        assertEquals(threat.getCount(), 2);
        assertEquals(threat.getLevel(), LibraryPolicyThreatLevel.Critical);
        assertTrue(threat.getComponents().contains( "component1##Open##0##Open:Review Requested##7"));
        assertTrue(threat.getComponents().contains( "component2##Open##0##Open##7"));
        assertEquals(threat.getDispositionCounts().size(), 1);
        assertEquals(threat.getDispositionCounts().get(LibraryPolicyThreatDisposition.Open).intValue(), 2);


        result.addThreat(LibraryPolicyType.License, LibraryPolicyThreatLevel.Critical, LibraryPolicyThreatDisposition.Closed, "Closed:False Positive", "component3","0","7");
        assertEquals(result.getThreats().size(), 1);
        assertEquals(result.getThreats().keySet().size(), 1);
        assertEquals(result.getThreats().values().size(), 1);
        assertEquals(result.getThreats().keySet().iterator().next(), LibraryPolicyType.License);
        threats = result.getThreats().get(LibraryPolicyType.License);
        assertEquals(threats.size(), 1);
        threat = threats.iterator().next();
        assertEquals(threat.getCount(), 3);
        assertEquals(threat.getLevel(), LibraryPolicyThreatLevel.Critical);
        assertTrue(threat.getComponents().contains( "component1##Open##0##Open:Review Requested##7"));
        assertTrue(threat.getComponents().contains( "component2##Open##0##Open##7"));
        assertTrue(threat.getComponents().contains( "component3##Closed##0##Closed:False Positive##7"));
        assertEquals(threat.getDispositionCounts().size(), 2);
        assertEquals(threat.getDispositionCounts().get(LibraryPolicyThreatDisposition.Open).intValue(), 2);
        assertEquals(threat.getDispositionCounts().get(LibraryPolicyThreatDisposition.Closed).intValue(), 1);

        result.addThreat(LibraryPolicyType.License, LibraryPolicyThreatLevel.High, LibraryPolicyThreatDisposition.Open, "Open:Legal Review Requested", "component4","0","7");
        assertEquals(result.getThreats().size(), 1);
        assertEquals(result.getThreats().keySet().size(), 1);
        assertEquals(result.getThreats().values().size(), 1);
        assertEquals(result.getThreats().keySet().iterator().next(), LibraryPolicyType.License);
        threats = result.getThreats().get(LibraryPolicyType.License);
        assertEquals(threats.size(), 2);
        assertEquals(threats.stream().filter(t -> Objects.equals(t.getLevel(), LibraryPolicyThreatLevel.Critical)).count(),1);
        assertEquals(threats.stream().filter(t -> Objects.equals(t.getLevel(), LibraryPolicyThreatLevel.High)).count(),1);
        threat = threats.stream().filter(t -> Objects.equals(t.getLevel(), LibraryPolicyThreatLevel.Critical)).findFirst().orElse(null);
        assertNotNull(threat);
        assertEquals(threat.getCount(), 3);
        assertEquals(threat.getLevel(), LibraryPolicyThreatLevel.Critical);
        assertTrue(threat.getComponents().contains( "component1##Open##0##Open:Review Requested##7"));
        assertTrue(threat.getComponents().contains( "component2##Open##0##Open##7"));
        assertTrue(threat.getComponents().contains( "component3##Closed##0##Closed:False Positive##7"));
        assertEquals(threat.getDispositionCounts().size(), 2);
        assertEquals(threat.getDispositionCounts().get(LibraryPolicyThreatDisposition.Open).intValue(), 2);
        assertEquals(threat.getDispositionCounts().get(LibraryPolicyThreatDisposition.Closed).intValue(), 1);
        threat = threats.stream().filter(t -> Objects.equals(t.getLevel(), LibraryPolicyThreatLevel.High)).findFirst().orElse(null);
        assertNotNull(threat);
        assertEquals(threat.getCount(), 1);
        assertEquals(threat.getLevel(), LibraryPolicyThreatLevel.High);
        assertTrue(threat.getComponents().contains( "component4##Open##0##Open:Legal Review Requested##7"));
        assertEquals(threat.getDispositionCounts().size(), 1);
        assertEquals(threat.getDispositionCounts().get(LibraryPolicyThreatDisposition.Open).intValue(), 1);


        result.addThreat(LibraryPolicyType.Security, LibraryPolicyThreatLevel.High, LibraryPolicyThreatDisposition.Open, "Open:Unconfirmed", "component5","0","7");
        assertEquals(result.getThreats().size(), 2);
        assertEquals(result.getThreats().keySet().size(), 2);
        assertEquals(result.getThreats().values().size(), 2);
        threats = result.getThreats().get(LibraryPolicyType.License);
        assertEquals(threats.size(), 2);
        assertEquals(threats.stream().filter(t -> Objects.equals(t.getLevel(), LibraryPolicyThreatLevel.Critical)).count(),1);
        assertEquals(threats.stream().filter(t -> Objects.equals(t.getLevel(), LibraryPolicyThreatLevel.High)).count(),1);
        threat = threats.stream().filter(t -> Objects.equals(t.getLevel(), LibraryPolicyThreatLevel.Critical)).findFirst().orElse(null);
        assertNotNull(threat);
        assertEquals(threat.getCount(), 3);
        assertEquals(threat.getLevel(), LibraryPolicyThreatLevel.Critical);
        assertTrue(threat.getComponents().contains( "component1##Open##0##Open:Review Requested##7"));
        assertTrue(threat.getComponents().contains( "component2##Open##0##Open##7"));
        assertTrue(threat.getComponents().contains( "component3##Closed##0##Closed:False Positive##7"));
        assertEquals(threat.getDispositionCounts().size(), 2);
        assertEquals(threat.getDispositionCounts().get(LibraryPolicyThreatDisposition.Open).intValue(), 2);
        assertEquals(threat.getDispositionCounts().get(LibraryPolicyThreatDisposition.Closed).intValue(), 1);
        threat = threats.stream().filter(t -> Objects.equals(t.getLevel(), LibraryPolicyThreatLevel.High)).findFirst().orElse(null);
        assertNotNull(threat);
        assertEquals(threat.getCount(), 1);
        assertEquals(threat.getLevel(), LibraryPolicyThreatLevel.High);
        assertTrue(threat.getComponents().contains( "component4##Open##0##Open:Legal Review Requested##7"));
        assertEquals(threat.getDispositionCounts().size(), 1);
        assertEquals(threat.getDispositionCounts().get(LibraryPolicyThreatDisposition.Open).intValue(), 1);

        threats = result.getThreats().get(LibraryPolicyType.Security);
        assertEquals(threats.size(), 1);
        threat = threats.stream().filter(t -> Objects.equals(t.getLevel(), LibraryPolicyThreatLevel.High)).findFirst().orElse(null);
        assertNotNull(threat);
        assertEquals(threat.getCount(), 1);
        assertEquals(threat.getLevel(), LibraryPolicyThreatLevel.High);
        assertTrue(threat.getComponents().contains( "component5##Open##0##Open:Unconfirmed##7"));
        assertEquals(threat.getDispositionCounts().size(), 1);
    }

    @Test
    public void addThreat1() {
    }
}