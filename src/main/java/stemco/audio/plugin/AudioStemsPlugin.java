/**
 * Copyright 2024- Will Drevo, Jon Marbry, Justin Belcher
 * <p>
 * TODO: tune this up as needed
 * <p>
 * Audio stems model and runner by:
 *
 * @author Will Drevo <will.drevo@gmail.com>
 * @author Jon Marbry <finarfin4169@gmail.com>
 * <p>
 * Chromatik modulators and plugin lead:
 * @author Justin Belcher <justin@jkb.studio>
 */
package stemco.audio.plugin;

import heronarts.lx.LX;
import heronarts.lx.LXPlugin;
import heronarts.lx.studio.LXStudio;

import stemco.audio.component.AudioStems;
import stemco.audio.component.UIAudioStems;

@LXPlugin.Name("Audio Stems")
public class AudioStemsPlugin implements LXStudio.Plugin {

  // This string must be manually updated to match the pom.xml version
  private static final String VERSION = "0.1.0";

  private AudioStems audioStems;

  public AudioStemsPlugin(LX lx) {
    LX.log("AudioStemPlugin(LX) version: " + VERSION);
  }

  @Override
  public void initialize(LX lx) {
    lx.engine.registerComponent("audioStems", this.audioStems = new AudioStems(lx));

    // This will get picked up by the package import, no need to directly add.
    // lx.registry.addModulator(AudioStemModulator.class);
  }

  @Override
  public void initializeUI(LXStudio lxStudio, LXStudio.UI ui) {

  }

  @Override
  public void onUIReady(LXStudio lxStudio, LXStudio.UI ui) {
    new UIAudioStems(ui, this.audioStems, ui.leftPane.global.getContentWidth())
      .addToContainer(ui.leftPane.global, 2);

    /* Pending upstream availability of performance tools panes
    new UIAudioStems(ui, this.audioStems, ui.leftPerformanceTools.getContentWidth())
      .addToContainer(ui.leftPerformanceTools, 0);
     */
  }

  @Override
  public void dispose() {
    this.audioStems.dispose();
  }
}
