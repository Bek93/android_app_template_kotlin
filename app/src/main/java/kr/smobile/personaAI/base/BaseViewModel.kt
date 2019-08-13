package kr.smobile.personaAI.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kr.smobile.personaAI.rx.SchedulerProvider
import java.lang.ref.WeakReference

open class BaseViewModel<T>(schedulerProvider: SchedulerProvider) : ViewModel() {

    var loading = ObservableBoolean(false)
    var schedulerProvider: SchedulerProvider
        get() = schedulerProvider
        set(value) {
            schedulerProvider = value
        }
    var compositeDisposable: CompositeDisposable
        get() = compositeDisposable;
        set(value) {
            compositeDisposable = value
        }
    var navigator: WeakReference<T>
        get() = navigator;
        set(value) {
            this.navigator = value
        }

    init {
        this.schedulerProvider = schedulerProvider
        this.compositeDisposable = CompositeDisposable();
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


    fun isLoading(): ObservableBoolean {
        return loading
    }

    fun setLoading(isLoading: Boolean) {
        loading.set(isLoading)
    }


}