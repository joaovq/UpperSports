package br.com.joaovq.uppersports.team.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.joaovq.uppersports.team.data.local.dao.TeamDao
import br.com.joaovq.uppersports.team.data.local.entity.TeamEntity

@Database(
    entities = [TeamEntity::class],
    version = 1
)
abstract class TeamDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao

   companion object {
       fun getInstance(context: Context) =
           Room.databaseBuilder(
               context,
               TeamDatabase::class.java,
               "upper_sports_team_db"
           ).build()
   }
}