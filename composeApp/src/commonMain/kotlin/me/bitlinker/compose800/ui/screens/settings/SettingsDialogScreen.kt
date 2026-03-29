package me.bitlinker.compose800.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.bitlinker.compose800.composeapp.generated.resources.Res
import me.bitlinker.compose800.composeapp.generated.resources.close_16px
import me.bitlinker.compose800.composeapp.generated.resources.settings_about_title
import me.bitlinker.compose800.composeapp.generated.resources.settings_privacy_policy_title
import me.bitlinker.compose800.composeapp.generated.resources.settings_sources_title
import me.bitlinker.compose800.composeapp.generated.resources.settings_theme_auto_title
import me.bitlinker.compose800.composeapp.generated.resources.settings_theme_dark_title
import me.bitlinker.compose800.composeapp.generated.resources.settings_theme_light_title
import me.bitlinker.compose800.composeapp.generated.resources.settings_theme_title
import me.bitlinker.compose800.composeapp.generated.resources.settings_title
import me.bitlinker.compose800.composeapp.generated.resources.settings_version_title
import me.bitlinker.compose800.repository.ThemeSetting
import me.bitlinker.compose800.ui.theme.Dimens
import me.bitlinker.compose800.ui.theme.TextStyles
import me.bitlinker.compose800.ui.theme.ThemeColors
import me.bitlinker.compose800.ui.views.IconButtonView
import me.bitlinker.compose800.ui.views.TextButtonView
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SettingsDialogScreen(
    state: SettingsDialogViewState,
    dispatcher: (SettingsDialogAction) -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = ThemeColors.current.frameBody,
                shape = RoundedCornerShape(Dimens.backgroundRadius)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = Dimens.paddingLarge,
                vertical = Dimens.paddingLarge
            )
        ) {
            SettingTitleText(
                text = stringResource(Res.string.settings_title),
                modifier = Modifier.weight(1F)
            )
            IconButtonView(
                painterResource(Res.drawable.close_16px),
                onClick = { dispatcher(SettingsDialogAction.OnDismissed) }
            )
        }
        Column(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.paddingLarge,
                    vertical = Dimens.paddingMedium
                )
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SettingSectionText(stringResource(Res.string.settings_theme_title))
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.paddingExtraSmall),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ThemeSetting.entries.forEach { themeSetting ->
                    TextButtonView(
                        text = stringResource(
                            when (themeSetting) {
                                ThemeSetting.Auto -> Res.string.settings_theme_auto_title
                                ThemeSetting.Light -> Res.string.settings_theme_light_title
                                ThemeSetting.Dark -> Res.string.settings_theme_dark_title
                            }
                        ),
                        isSelected = themeSetting == state.theme,
                        onClick = { dispatcher(SettingsDialogAction.OnThemeChanged(themeSetting)) }
                    )
                }
            }
            SettingSectionText(stringResource(Res.string.settings_about_title))
            TextButtonView(
                text = stringResource(Res.string.settings_privacy_policy_title),
                onClick = { dispatcher(SettingsDialogAction.OnPrivacyPolicyClicked) },
            )
            TextButtonView(
                text = stringResource(Res.string.settings_sources_title),
                onClick = { dispatcher(SettingsDialogAction.OnSourcesClicked) },
            )
            SettingSectionText(stringResource(Res.string.settings_version_title, state.version))
        }
    }
}

@Composable
private fun SettingTitleText(
    text: String,
    modifier: Modifier = Modifier,
) {
    val titleLabelColor = ThemeColors.current.titleLabel
    BasicText(
        text = text,
        style = TextStyles.titleTextStyle,
        color = { titleLabelColor },
        modifier = modifier,
    )
}

@Composable
private fun SettingSectionText(
    text: String,
    modifier: Modifier = Modifier,
) {
    val titleLabelColor = ThemeColors.current.titleLabel
    BasicText(
        text = text,
        style = TextStyles.descriptionTextStyle,
        color = { titleLabelColor },
        modifier = modifier,
    )
}