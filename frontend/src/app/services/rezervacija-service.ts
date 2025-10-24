import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rezervacija } from '../models/rezervacija';

@Injectable({
  providedIn: 'root',
})
export class RezervacijaService {
  private readonly API_URL = 'http://localhost:8080/rezervacija';

  constructor(private httpClient: HttpClient) {}

  public getAllRezervacije(): Observable<Rezervacija[]> {
    return this.httpClient.get<Rezervacija[]>(this.API_URL);
  }

  public createRezervacija(rezervacija: Rezervacija): Observable<Rezervacija> {
    return this.httpClient.post<Rezervacija>(this.API_URL, rezervacija);
  }

  public updateRezervacija(rezervacija: Rezervacija): Observable<Rezervacija> {
    return this.httpClient.put<Rezervacija>(`${this.API_URL}/${rezervacija.id}`, rezervacija);
  }

  public deleteRezervacija(id: number): Observable<string> {
    return this.httpClient.delete(`${this.API_URL}/${id}`, { responseType: 'text' });
  }
}
