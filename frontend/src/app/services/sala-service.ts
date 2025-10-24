import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sala } from '../models/sala';
import { Bioskop } from '../models/bioskop';

@Injectable({
  providedIn: 'root',
})
export class SalaService {
  private readonly API_URL = 'http://localhost:8080/sala';

  constructor(private httpClient: HttpClient) {}

  public getAllSalas(): Observable<Sala[]> {
    return this.httpClient.get<Sala[]>(this.API_URL);
  }

  public createSala(sala: Sala): Observable<Sala> {
    return this.httpClient.post<Sala>(this.API_URL, sala);
  }

  public updateSala(sala: Sala): Observable<Sala> {
    return this.httpClient.put<Sala>(`${this.API_URL}/${sala.id}`, sala);
  }

  public deleteSala(id: number): Observable<string> {
    return this.httpClient.delete(`${this.API_URL}/${id}`, { responseType: 'text' });
  }
}
