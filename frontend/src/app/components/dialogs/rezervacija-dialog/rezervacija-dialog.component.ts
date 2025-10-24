import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RezervacijaService } from '../../../services/rezervacija-service';
import { Rezervacija } from '../../../models/rezervacija';
import { FilmService } from '../../../services/film-service';
import { SalaService } from '../../../services/sala-service';
import { Film } from '../../../models/film';
import { Sala } from '../../../models/sala';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

@Component({
  selector: 'app-rezervacija-dialog',
  standalone: true,
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './rezervacija-dialog.component.html',
  styleUrls: ['./rezervacija-dialog.component.css'],
})
export class RezervacijaDialog implements OnInit {
  flag!: number;
  filmovi!: Film[];
  sale!: Sala[];

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<RezervacijaDialog>,
    private rezervacijaService: RezervacijaService,
    private filmService: FilmService,
    private salaService: SalaService,
    @Inject(MAT_DIALOG_DATA) public data: Rezervacija
  ) {}

  ngOnInit(): void {
    this.filmService.getAllFilms().subscribe((res) => (this.filmovi = res));
    this.salaService.getAllSalas().subscribe((res) => (this.sale = res));
  }

  add(): void {
    this.rezervacijaService.createRezervacija(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Rezervacija ID ${data.id} uspešno dodata!`, 'OK', { duration: 2500 });
      },
      error: (err) => console.error(err),
    });
  }

  update(): void {
    this.rezervacijaService.updateRezervacija(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Rezervacija ID ${data.id} uspešno izmenjena!`, 'OK', { duration: 2500 });
      },
      error: (err) => console.error(err),
    });
  }

  delete(): void {
    this.rezervacijaService.deleteRezervacija(this.data.id).subscribe({
      next: (response) => {
        this.dialogRef.close(1);
        this.snackBar.open(response, 'OK', { duration: 2500 });
      },
      error: (err) => console.error(err),
    });
  }

  cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste od izmene', 'OK', { duration: 2000 });
  }

  compare(a: any, b: any): boolean {
    return a.id === b.id;
  }
}
