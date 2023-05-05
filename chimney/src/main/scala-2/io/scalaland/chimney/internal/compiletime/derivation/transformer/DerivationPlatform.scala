package io.scalaland.chimney.internal.compiletime.derivation.transformer

import io.scalaland.chimney.internal.compiletime.DefinitionsPlatform

private[derivation] trait DerivationPlatform
    extends Derivation
    with rules.TransformSubtypesRuleModule
    with rules.TransformOptionToOptionRuleModule
    with rules.LegacyMacrosFallbackRuleModule {
  this: DefinitionsPlatform =>

  override protected val rulesAvailableForPlatform: List[Rule] =
    List(TransformSubtypesRule, TransformOptionToOptionRule, LegacyMacrosFallbackRule)
}
