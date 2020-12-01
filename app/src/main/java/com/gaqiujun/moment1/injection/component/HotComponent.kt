package com.gaqiujun.moment1.injection.component

import com.gaqiujun.moment1.module.hot.HotFrag
import com.mingo.baselibrary.injection.PerComponentScope
import com.mingo.baselibrary.injection.component.ActivityComponent
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class])
interface HotComponent {
    fun inject(frag: HotFrag)
}