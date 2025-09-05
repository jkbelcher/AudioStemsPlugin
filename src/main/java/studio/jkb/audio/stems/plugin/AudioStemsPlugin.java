/**
 * Copyright 2024- Justin K. Belcher, Will Drevo, Jon Marbry
 * Chromatik plugin and modulators:
 * @author Justin K. Belcher <justin@jkb.studio>
 * Audio stems model and runner:
 * @author Will Drevo <will.drevo@gmail.com>
 * @author Jon Marbry <finarfin4169@gmail.com>
 */

package studio.jkb.audio.stems.plugin;

import heronarts.lx.LX;
import heronarts.lx.LXPlugin;
import heronarts.lx.studio.LXStudio;

import studio.jkb.audio.stems.LOG;
import studio.jkb.audio.stems.component.AudioStems;
import studio.jkb.audio.stems.component.UIAudioStems;
import studio.jkb.audio.stems.modulator.AudioStemModulator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@LXPlugin.Name("Audio Stems")
public class AudioStemsPlugin implements LXStudio.Plugin {

  private AudioStems audioStems;

  public AudioStemsPlugin(LX lx) {
    LOG.log("AudioStemsPlugin(LX) version: " + loadVersion());
  }

  @Override
  public void initialize(LX lx) {
    lx.engine.registerComponent("audioStems", this.audioStems = new AudioStems(lx));

    // This will get picked up by the package import, no need to directly add.
    // lx.registry.addModulator(AudioStemModulator.class);
  }

  @Override
  public void initializeUI(LXStudio lxStudio, LXStudio.UI ui) { }

  @Override
  public void onUIReady(LXStudio lxStudio, LXStudio.UI ui) {
    new UIAudioStems(ui, this.audioStems, ui.leftPane.global.getContentWidth())
      .addToContainer(ui.leftPane.global, 2);

    new UIAudioStems(ui, this.audioStems, ui.leftPerformance.tools.getContentWidth())
      .addToContainer(ui.leftPerformance.tools, 0);
  }

  @Override
  public void dispose() {
    this.audioStems.dispose();
  }

  /**
   * Projects that import this library and are NOT an LXPackage (such as a custom build)
   * should call this method from their initialize().
   */
  public static void registerComponents(LX lx) {
    lx.registry.addModulator(AudioStemModulator.class);
  }

  /**
   * Loads 'audioStems.properties', after maven resource filtering has been applied.
   */
  private String loadVersion() {
    String version = "";
    Properties properties = new Properties();
    try (InputStream inputStream =
           getClass().getClassLoader().getResourceAsStream("audioStems.properties")) {
      properties.load(inputStream);
      version = properties.getProperty("audioStems.version");
    } catch (IOException e) {
      LOG.error("Failed to load version information " + e);
    }
    return version;
  }
}
