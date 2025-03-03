plugins {
    `common-conventions`
    
    `bukkit-conventions`
}

dependencies {
    api(project(":augmented-cabin:augmented-cabin-api"))
}

paper {
    main = "dev.slne.augmented.cabin.bukkit.CabinBukkitPlugin"
}