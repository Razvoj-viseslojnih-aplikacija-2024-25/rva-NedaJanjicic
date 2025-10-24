import { Component, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FilmService } from '../../../services/film-service';
import { Film } from '../../../models/film';

@Component({
  selector: 'app-film-dialog',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatButtonModule, MatInputModule, FormsModule, CommonModule],
  templateUrl: './film-dialog.component.html',
  styleUrls: ['./film-dialog.component.css']
})
export class FilmDialog {
  flag!: number;

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<FilmDialog>,
    private filmService: FilmService,
    @Inject(MAT_DIALOG_DATA) public data: Film
  ) {}

  add(): void {
    this.filmService.createFilm(this.data).subscribe({
      next: (film) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Film "${film.naziv}" uspešno dodat!`, 'OK', { duration: 2500 });
      },
      error: (error) => console.log(error.message)
    });
  }

  update(): void {
    this.filmService.updateFilm(this.data).subscribe({
      next: (film) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Film "${film.naziv}" uspešno ažuriran!`, 'OK', { duration: 2500 });
      },
      error: (error) => console.log(error.message)
    });
  }

  delete(): void {
    this.filmService.deleteFilm(this.data.id).subscribe({
      next: (response) => {
        this.dialogRef.close(1);
        this.snackBar.open(response, 'OK', { duration: 2500 });
      },
      error: (error) => console.log(error.message)
    });
  }

  cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste od izmene', 'OK', { duration: 2000 });
  }
}
