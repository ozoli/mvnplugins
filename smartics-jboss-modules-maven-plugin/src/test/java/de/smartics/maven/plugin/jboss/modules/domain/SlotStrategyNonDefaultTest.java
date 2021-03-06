package de.smartics.maven.plugin.jboss.modules.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests {@link de.smartics.maven.plugin.jboss.modules.domain.SlotStrategy} with the non default
 * ie. non MAIN slot strategy.
 */
@RunWith(Parameterized.class)
public class SlotStrategyNonDefaultTest extends AbstractSlotStrategyTest {

  // The default slot strategy, normally main
  private String defaultNonMainSlotStrategy;

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"1.2.3", SlotStrategy.VERSION_MAJOR, "non-main", "non-main1"},
        {"4.5", SlotStrategy.VERSION_MAJOR, "non-default", "non-default4"},
        {"4.5", SlotStrategy.VERSION_MAJOR_MINOR, "anotherDefault", "anotherDefault4.5"},
        {"4.5.7", SlotStrategy.VERSION_MAJOR_MINOR, "anotherDefault", "anotherDefault4.5"},
        {"4", SlotStrategy.VERSION_MAJOR_MINOR, "anotherDefault", "anotherDefault4.0"},
        {"4.5", SlotStrategy.VERSION_FULL, "semi-osgi-slot", "semi-osgi-slot4.5.0"}
    });
  }

  /**
   * For each test create an artifact and use {@link de.smartics.maven.plugin.jboss.modules.domain.SlotStrategy}
   * to calculate the version and test it against the expected version.
   *
   * @param artifactVersion the version of the {@link org.eclipse.aether.artifact.Artifact} to create.
   * @param slotStrategy the {@link de.smartics.maven.plugin.jboss.modules.domain.SlotStrategy} to test.
   * @param defaultNonMainSlotStrategy the default slot strategy non Main
   * @param expectedVersion the expected version.
   */
  public SlotStrategyNonDefaultTest(final String artifactVersion, final SlotStrategy slotStrategy,
                                    final String defaultNonMainSlotStrategy, final String expectedVersion) {
    super(artifactVersion, slotStrategy, expectedVersion);
    this.defaultNonMainSlotStrategy = defaultNonMainSlotStrategy;
  }

  @Test
  public void testVersionForMainDefaultSlotStrategy() {
    assertNotEquals("default non main slot strategy incorrect", SlotStrategy.MAIN_SLOT, defaultNonMainSlotStrategy);
    assertEquals("strategy version for non default slot incorrect", expectedVersion,
        slotStrategy.calculateSlot(version, defaultNonMainSlotStrategy));
  }

  @Test
  public void testVersionForMainDefaultSlotStrategyFromString() {
    assertEquals("strategy version for non default slot incorrect", expectedVersion,
        SlotStrategy.fromString(slotStrategy.toString()).calculateSlot(version, defaultNonMainSlotStrategy));
  }

}
