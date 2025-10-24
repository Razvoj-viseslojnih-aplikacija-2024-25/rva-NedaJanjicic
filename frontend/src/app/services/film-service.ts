import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Film } from '../models/film';

@Injectable({
  providedIn: 'root',
})
export class FilmService {
  private readonly API_URL = 'http://localhost:8080/film';

  constructor(private httpClient: HttpClient) {}

  public getAllFilms(): Observable<Film[]> {
    return this.httpClient.get<Film[]>(this.API_URL);
  }

  public createFilm(film: Film): Observable<Film> {
    return this.httpClient.post<Film>(this.API_URL, film);
  }

  public updateFilm(film: Film): Observable<Film> {
    return this.httpClient.put<Film>(`${this.API_URL}/${film.id}`, film);
  }

  public deleteFilm(id: number): Observable<string> {
    return this.httpClient.delete(`${this.API_URL}/${id}`, { responseType: 'text' });
  }
}
