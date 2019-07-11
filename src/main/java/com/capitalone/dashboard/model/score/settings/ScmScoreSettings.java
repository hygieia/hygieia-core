package com.capitalone.dashboard.model.score.settings;

/**
 * Bean to hold score settings specific to scm
 */
public class ScmScoreSettings extends ScoreComponentSettings {

  public static final int SCM_NUM_OF_DAYS = 14;

  private ScoreComponentSettings daysWithCommits;

  private int numberOfDays = SCM_NUM_OF_DAYS;

  public static ScmScoreSettings cloneScmScoreSettings(ScmScoreSettings scmScoreSettings) {
    if (null == scmScoreSettings) {
      return null;
    }

    ScmScoreSettings scmScoreSettingsClone = new ScmScoreSettings();
    ScoreComponentSettings.copyScoreComponentSettings(scmScoreSettings, scmScoreSettingsClone);
    scmScoreSettingsClone.setNumberOfDays(
      scmScoreSettings.getNumberOfDays()
    );
    scmScoreSettingsClone.setDaysWithCommits(
                ScoreComponentSettings.cloneScoreComponentSettings(scmScoreSettings.getDaysWithCommits())
    );

    return scmScoreSettingsClone;
  }

  public int getNumberOfDays() {
    return numberOfDays;
  }

  public void setNumberOfDays(int numberOfDays) {
    this.numberOfDays = numberOfDays;
  }

    public ScoreComponentSettings getDaysWithCommits() {
    return daysWithCommits;
  }

  public void setDaysWithCommits(ScoreComponentSettings daysWithCommits) {
    this.daysWithCommits = daysWithCommits;
  }

  @Override public String toString() {
    return "ScmScoreSettings{" +
      "commitsPerDay=" + daysWithCommits +
      ", numberOfDays=" + numberOfDays +
      ", disabled=" + isDisabled() +
      ", weight=" + getWeight() +
      ", criteria=" + getCriteria() +
      '}';
  }
}
