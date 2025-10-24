import { Component, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Bioskop } from '../../../models/bioskop';
import { BioskopService } from '../../../services/bioskop-service';

@Component({
  selector: 'app-bioskop-dialog',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatButtonModule, MatInputModule, FormsModule, CommonModule],
  templateUrl: './bioskop-dialog.component.html',
  styleUrls: ['./bioskop-dialog.component.css']
})
export class BioskopDialog {
  flag!: number;

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<BioskopDialog>,
    private bioskopService: BioskopService,
    @Inject(MAT_DIALOG_DATA) public data: Bioskop
  ) {}

  add(): void {
    this.bioskopService.createBioskop(this.data).subscribe({
      next: (result) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Bioskop "${result.naziv}" uspešno dodat!`, 'OK', { duration: 2500 });
      },
      error: (err) => console.error(err)
    });
  }

  update(): void {
    this.bioskopService.updateBioskop(this.data).subscribe({
      next: (result) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Bioskop "${result.naziv}" uspešno izmenjen!`, 'OK', { duration: 2500 });
      },
      error: (err) => console.error(err)
    });
  }

  delete(): void {
    this.bioskopService.deleteBioskop(this.data.id).subscribe({
      next: (response) => {
        this.dialogRef.close(1);
        this.snackBar.open(response, 'OK', { duration: 2500 });
      },
      error: (err) => console.error(err)
    });
  }

  cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste od izmene', 'OK', { duration: 2000 });
  }
}
