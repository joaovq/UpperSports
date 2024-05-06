package br.com.joaovq.uppersports.team.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import br.com.joaovq.uppersports.team.data.local.entity.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Query("select * from team_tb")
    fun getTeams(): Flow<List<TeamEntity>>
    @Query("select * from team_tb where name like '%' || :query || '%'")
    fun getTeamsByQuery(query: String): Flow<List<TeamEntity>>

    @Query("select * from team_tb where id=:id")
    fun getTeamById(id: Int): Flow<TeamEntity>

    @Insert
    suspend fun insertTeam(teamEntity: TeamEntity)
    @Upsert
    suspend fun upsertTeams(teams: List<TeamEntity>)
    @Delete
    suspend fun deleteTeam(teamEntity: TeamEntity)
    @Query("delete from team_tb")
    suspend fun deleteAll()
}