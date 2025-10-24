import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bioskop } from '../models/bioskop';

@Injectable({
  providedIn: 'root'
})
export class BioskopService {
  private readonly API_URL = 'http://localhost:8080/bioskop';

  constructor(private httpClient: HttpClient) {}

  public getAllBioskops(): Observable<Bioskop[]> {
    return this.httpClient.get<Bioskop[]>(this.API_URL);
  }

  public createBioskop(bioskop: Bioskop): Observable<Bioskop> {
    return this.httpClient.post<Bioskop>(this.API_URL, bioskop);
  }

  public updateBioskop(bioskop: Bioskop): Observable<Bioskop> {
    return this.httpClient.put<Bioskop>(`${this.API_URL}/${bioskop.id}`, bioskop);
  }

  public deleteBioskop(id: number): Observable<string> {
    return this.httpClient.delete(`${this.API_URL}/${id}`, { responseType: 'text' });
  }
}
