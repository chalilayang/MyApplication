package com.example.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project;

class AsmPluginImp implements Plugin<Project> {
    @Override
    void apply(Project project) {
        System.out.println("==LifeCyclePlugin gradle plugin==")
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new CostTimePlugin())
    }
}