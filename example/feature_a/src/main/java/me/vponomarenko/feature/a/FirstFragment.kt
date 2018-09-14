package me.vponomarenko.feature.a

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*
import me.vponomarenko.core.TextHolder
import me.vponomarenko.feature.a.di.DaggerFirstFeatureComponent
import me.vponomarenko.feature.a.di.FirstFeatureComponent
import me.vponomarenko.injectionmanager.IHasComponent
import me.vponomarenko.injectionmanager.InjectionManager
import javax.inject.Inject

/**
 * Author: Valery Ponomarenko
 * Date: 24/08/2018
 * LinkedIn: https://www.linkedin.com/in/ponomarenkovalery
 */

class FirstFragment : Fragment(), IHasComponent {

    @Inject
    lateinit var textHolder: TextHolder

    private val textHolderObserver = { changedText: String ->
        text.text = changedText
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_first, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectionManager.instance.bindComponent<FirstFeatureComponent>(this).inject(this)
    }

    override fun onResume() {
        super.onResume()
        textHolder.subscribe(textHolderObserver)
    }

    override fun onPause() {
        super.onPause()
        textHolder.unsubscribe(textHolderObserver)
    }

    override fun getComponent(): FirstFeatureComponent =
        DaggerFirstFeatureComponent.builder()
            .appDependency(InjectionManager.instance.findComponent())
            .build()
}